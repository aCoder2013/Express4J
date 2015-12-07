package org.express4j.core;

import org.express4j.handler.Handler;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.express4j.router.DefaultRouterFactory;
import org.express4j.webserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Core Filter
 * Interceptor all the request,dispatch
 * to the corresponding handler
 * Created by Song on 2015/12/4.
 */
@WebFilter(urlPatterns = "/*")
public class CoreFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CoreFilter.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    


    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CoreFilter Init");
    }




    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //向下转型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //设置编码
        request.setCharacterEncoding(DEFAULT_CHARSET);
        response.setCharacterEncoding(DEFAULT_CHARSET);

        String path = getPath(request);
        Handler handler = DefaultRouterFactory.getHandler(request.getMethod(),path);
            if (handler!=null) {
                try {
                    handler.handle(new Request(request),new Response(response));
                } catch (Exception e) {
                    logger.error("Execute Handler Failure",e);
                    throw new RuntimeException(e);
                }
            }
    }

    /**
     * 得到路径
     * 截取掉ContextPath,例如/ProjectName的形式
     * 如果以/结尾，也截取掉
     * @param request
     * @return
     */
    private String getPath(HttpServletRequest request) {
        String path = "";
        String contextPath = request.getContextPath();
        if (contextPath!="/") {
            path = request.getRequestURI().substring(contextPath.length());
        }
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-1);
        }
        return path;
    }

    public void destroy() {
        JettyServer.stop();
    }
}
