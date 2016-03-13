package org.express4j.http.mapping;

/**
 * Created by Song on 2015/12/4.
 */
public class RequestMapping {

    private String method ;

    private String pathPattern;

    public RequestMapping(String method, String pathPattern) {
        this.method = method;
        this.pathPattern = pathPattern;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestMapping requestMapping = (RequestMapping) o;

        if (method != null ? !method.equals(requestMapping.method) : requestMapping.method != null) return false;
        return !(pathPattern != null ? !pathPattern.equals(requestMapping.pathPattern) : requestMapping.pathPattern != null);
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (pathPattern != null ? pathPattern.hashCode() : 0);
        return result;
    }
}
