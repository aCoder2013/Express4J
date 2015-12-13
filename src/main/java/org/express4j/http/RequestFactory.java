package org.express4j.http;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Song on 2015/12/12.
 */
public class RequestFactory {

    private static ThreadLocal<Request> REQUEST = new ThreadLocal<>();


    public static void create(HttpServletRequest req) {
        REQUEST.set(new Request(req));
    }

    public static Request getRequest() {
        return REQUEST.get();
    }

    public static void remove(){
        REQUEST.remove();
    }
}
