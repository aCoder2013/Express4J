package org.express4j.handler;

import org.express4j.http.*;

/**
 * Created by Song on 2015/12/4.
 */
@FunctionalInterface
public interface Handler {

    /**
     * 当处理对应路由的请求时调用
     * 例如: '/news'
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     * @return The content to be set in the response
     * @throws java.lang.Exception implementation can choose to throw exception
     */
    void handle(Request request, Response response) throws Exception;
}
