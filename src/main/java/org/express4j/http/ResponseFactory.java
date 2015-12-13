package org.express4j.http;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Song on 2015/12/12.
 */
public class ResponseFactory {

    private static ThreadLocal<Response> Response = new ThreadLocal<>();


    public static void create(HttpServletResponse res) {
        Response.set(new Response(res));
    }

    public static Response getResponse() {
        return Response.get();
    }

    public static void remove(){
        Response.remove();
    }
}
