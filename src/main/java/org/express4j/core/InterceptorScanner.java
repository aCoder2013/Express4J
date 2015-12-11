package org.express4j.core;

import org.express4j.annotation.ExceptionHandler;
import org.express4j.annotation.ExceptionInterceptor;
import org.express4j.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Song on 2015/12/11.
 */
public class InterceptorScanner {

    private  Set<Class<?>> intercetporSet = new HashSet<>();
    private  Map<Class<?>,ClassAndMethod> methodMap = new HashMap<>();

    public void init() {
        loadInterceptorClass();
        if(!intercetporSet.isEmpty()){
            for(Class<?> cls : intercetporSet){
                Method[] methods = cls.getMethods();
                for(Method m : methods){
                    if(m.isAnnotationPresent(ExceptionHandler.class)){
                        ExceptionHandler handler = m.getAnnotation(ExceptionHandler.class);
                        Class<?> exceptionClass = handler.value();
                        methodMap.put(exceptionClass,new ClassAndMethod(cls,m));
                    }
                }
            }
        }
    }

    /**
     * 加载标有ExceptionInterceptor注解的类
     */
    private void loadInterceptorClass() {
        Set<Class<?>> classSet = ClassUtils.getClassSet();
        intercetporSet.addAll(classSet.stream()
                .filter(cls -> cls.isAnnotationPresent(ExceptionInterceptor.class))
                .collect(Collectors.toList()));
    }

    public  Set<Class<?>> getIntercetporSet() {
        return intercetporSet;
    }

    public  Map<Class<?>, ClassAndMethod> getMethodMap() {
        return methodMap;
    }

    public class ClassAndMethod{
        private Class aClass;
        private Method aMethod;

        public ClassAndMethod(Class aClass, Method aMethod) {
            this.aClass = aClass;
            this.aMethod = aMethod;
        }

        public Class getaClass() {
            return aClass;
        }

        public void setaClass(Class aClass) {
            this.aClass = aClass;
        }

        public Method getaMethod() {
            return aMethod;
        }

        public void setaMethod(Method aMethod) {
            this.aMethod = aMethod;
        }
    }
}
