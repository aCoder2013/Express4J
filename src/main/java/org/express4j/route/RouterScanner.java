package org.express4j.route;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.express4j.http.mapping.MethodParamWrapper;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Song on 2015/12/24.
 */
public class RouterScanner {

    public static final Logger logger = LoggerFactory.getLogger(RouterScanner.class);
    private static final String ROUTE_FILE_NAME = "routes";


    public static void init() {
        String path = ClassUtils.getResourcePath(ROUTE_FILE_NAME);
        try {
            FileReader fileReader = new FileReader(new File(path));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().replaceAll(" +", " ");
                String[] requestMapping = line.split(" ");
                String method = requestMapping[0];
                String requestPath = requestMapping[1];
                String handler = requestMapping[2];
                ClassAndMethod classAndMethod = parseHandler(handler);
                RequestMappingFactory.addMapping(method, requestPath, classAndMethod.getCls(), classAndMethod.getMethod(),classAndMethod.getParams());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String,Class<?>> parseMethodParams(String str) {
        OrderedMap params = new ListOrderedMap();
        if (str.contains("(") && str.contains(")")) {
            String target = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
            String[] nameAndTypes = target.split(",");
            for (String nameAndType : nameAndTypes) {
                String[] paramWrapper = nameAndType.split(":");
                String name = paramWrapper[0];
                String type = paramWrapper[1];
                try {
                    params.put(name,Class.forName(type));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }


    /**
     * 解析routes文件中的控制器段
     * eg:GET /hello controller.HelloController.hello中的controller.HelloController.hello段
     *
     * @param fullName
     * @return
     */
    private static ClassAndMethod parseHandler(String fullName) {
        String classAndMethodString = fullName;
        if (fullName.contains("(")) {
            classAndMethodString = fullName.substring(0,fullName.indexOf("("));
        }
        String className = classAndMethodString.substring(0, classAndMethodString.lastIndexOf("."));
        ClassAndMethod classAndMethod = new ClassAndMethod();
        try {
            Class<?> cls = Class.forName(className);
            int start =  classAndMethodString.lastIndexOf(".");
            String methodName = fullName.substring(start+1);
            if (fullName.contains("(")) {
                methodName = classAndMethodString.substring(start+ 1);
            }
            Map<String,Class<?>> params = parseMethodParams(fullName);//解析的请求参数
            LinkedList<MethodParamWrapper> paramName = new LinkedList<>();
            Class[] paramType = new Class[params.size()];//参数类型
            int i = 0;
            for(Map.Entry<String,Class<?>> entry : params.entrySet()){
                paramName.addLast(new MethodParamWrapper(entry.getKey(),entry.getValue()));
                paramType[i++] = entry.getValue();
            }
            //todo 解析方法参数
            Method method = cls.getDeclaredMethod(methodName,paramType);
            classAndMethod.setParams(paramName);
            classAndMethod.setCls(cls);
            classAndMethod.setMethod(method);
            /**
             * 控制器必须返回Handler
             * handler = (Handler) method.invoke(obj);
             */
        } catch (ClassNotFoundException e) {
            logger.error("Class not found : " + className);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            logger.error("No such method : : " + fullName);
            e.printStackTrace();
        }
        return classAndMethod;
    }

    private static class ClassAndMethod {
        private Class<?> cls;
        private Method method;
        private LinkedList<MethodParamWrapper> params;

        public Class<?> getCls() {
            return cls;
        }

        public void setCls(Class<?> cls) {
            this.cls = cls;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public LinkedList<MethodParamWrapper> getParams() {
            return params;
        }

        public void setParams(LinkedList<MethodParamWrapper> params) {
            this.params = params;
        }
    }
}
