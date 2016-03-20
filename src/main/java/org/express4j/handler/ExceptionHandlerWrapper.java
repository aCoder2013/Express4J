package org.express4j.handler;

import java.lang.reflect.Method;

/**
 * 封装异常处理器
 * Created by song on 16-3-20.
 */
public class ExceptionHandlerWrapper {

    private Class aClass;
    private Method aMethod;
    private int statusCode;

    public ExceptionHandlerWrapper(Class aClass, Method aMethod) {
        this.aClass = aClass;
        this.aMethod = aMethod;
    }

    public ExceptionHandlerWrapper(Class aClass, Method aMethod, int statusCode) {
        this.aClass = aClass;
        this.aMethod = aMethod;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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
