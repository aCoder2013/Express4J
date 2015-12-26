package org.express4j.http.mapping;

/**
 * Created by Song on 2015/12/26.
 */
public class MethodParamWrapper implements Order {

    private int priority = 0 ;

    private String paramName = "";

    private Object value = null;

    private Class<?> paramType = null;


    public MethodParamWrapper(String paramName, Class<?> paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public MethodParamWrapper(int priority, String paramName) {
        this.priority = priority;
        this.paramName = paramName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    @Override
    public int priority() {
        return 0;
    }
}
