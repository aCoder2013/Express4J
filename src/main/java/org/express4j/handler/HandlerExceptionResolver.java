package org.express4j.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song on 16-3-20.
 */
public interface HandlerExceptionResolver {


    void resolveException(HttpServletRequest request, HttpServletResponse response,Handler handler,Exception e);

}
