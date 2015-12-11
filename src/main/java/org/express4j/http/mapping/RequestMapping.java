package org.express4j.http.mapping;

/**
 * Created by Song on 2015/12/4.
 */
public class RequestMapping {

    private String method ;

    private String path;

    public RequestMapping(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestMapping requestMapping = (RequestMapping) o;

        if (method != null ? !method.equals(requestMapping.method) : requestMapping.method != null) return false;
        return !(path != null ? !path.equals(requestMapping.path) : requestMapping.path != null);

    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
