package org.express4j.http;

/**
 * Created by Song on 2015/12/4.
 */
public class Request {

    private String baseUrl ;

    private String method ;


    public Request setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public String baseUrl(){
        return this.baseUrl;
    }

    public String method(){
        return method.toUpperCase();
    }


}

