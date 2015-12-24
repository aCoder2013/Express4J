package org.express4j.route;

import org.express4j.handler.Handler;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Song on 2015/12/24.
 */
public class RouterScanner {

    public static final Logger logger = LoggerFactory.getLogger(RouterScanner.class);
    private static final String ROUTE_FILE_NAME = "routes";



    public static void init(){
        String path = ClassUtils.getResourcePath(ROUTE_FILE_NAME);
        try {
            FileReader fileReader = new FileReader(new File(path));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine())!= null){
                line = line.trim().replaceAll(" +", " ");
                String[] requestMapping = line.split(" ");
                String method = requestMapping[0];
                String requestPath = requestMapping[1];
                String handler = requestMapping[2];
                RequestMappingFactory.addMapping(method, requestPath, parseHandler(handler));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Handler parseHandler(String fullName) {
        Handler handler = null;
        String className = fullName.substring(0,fullName.lastIndexOf("."));
        try {
            Class<?> cls = Class.forName(className);
            String methodName = fullName.substring(fullName.lastIndexOf(".")+1);
            Method method = cls.getMethod(methodName);
            Object obj = cls.newInstance();
            handler = (Handler) method.invoke(obj);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found : "+className);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            logger.error("No such method : : "+ fullName);
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.error("Initialize class failure :" +className);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("Access class failure :" +className);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("Invoke method failure :" + fullName);
            e.printStackTrace();
        }
        return handler;
    }

}
