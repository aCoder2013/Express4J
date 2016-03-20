package org.express4j.servlet;

import org.apache.commons.io.IOUtils;
import org.express4j.aop.AopFactory;
import org.express4j.aop.Interceptor;
import org.express4j.core.Express4JConfig;
import org.express4j.core.InterceptorScanner;
import org.express4j.handler.DefaultHandlerExceptionResolver;
import org.express4j.handler.HandlerExceptionResolver;
import org.express4j.handler.HandlerExecutionChain;
import org.express4j.http.RequestFactory;
import org.express4j.http.ResponseFactory;
import org.express4j.http.mapping.HandlerWrapper;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.multipart.FileUploadHelper;
import org.express4j.route.RouterScanner;
import org.express4j.utils.ClassUtils;
import org.express4j.utils.ObjectUtils;
import org.express4j.utils.PathUtils;
import org.express4j.webserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static ServletContext servletContext;


    private HandlerExceptionResolver exceptionResolver;


    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CoreFilter Init");
        servletContext = filterConfig.getServletContext();
        initMultipart();
        initInterceptor();
        initRouter();
        initExceptionResolver();
    }

    private void initExceptionResolver() {
        exceptionResolver = new DefaultHandlerExceptionResolver();
    }

    private void initRouter() {
        RouterScanner.init();
    }

    private void initInterceptor() {
        InterceptorScanner.init();
    }

    private void initMultipart() {
        FileUploadHelper.init(servletContext);
    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HandlerExecutionChain handlerChain = null;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //设置编码
        request.setCharacterEncoding(DEFAULT_CHARSET);
        response.setCharacterEncoding(DEFAULT_CHARSET);

        String path = PathUtils.canonicalize(request);
        RequestFactory.create(request);
        ResponseFactory.create(response);

        if (request.getRequestURI().equals("/favicon.ico")) {
            return;
        }

        handlerChain = getHandler(request);
        if (handlerChain != null) {
            try {
                if(!handlerChain.applyPreHandle(request, response)){
                    return;
                }
                handlerChain.invoke(request, response);
                handlerChain.applyAfterHandler(request, response);
            } catch (Exception e) {
                //处理异常
                exceptionResolver.resolveException(request,response,handlerChain.getHandler(),e);
            } finally {
                RequestFactory.remove();
                ResponseFactory.remove();
            }
        } else {
            if (path.startsWith("http") || path.startsWith("https")) {
                response.sendRedirect(path);
            } else {
                String resourcePath = ClassUtils.getResourcePath(Express4JConfig.getStaticFilePath() + path);
                if(resourcePath !=null && !resourcePath.isEmpty()){
                    IOUtils.copy(new FileInputStream(resourcePath), response.getOutputStream());
                }else {
                    logger.warn("Can't find mapping for path :" + path);
                }
            }
        }
    }

    private HandlerExecutionChain getHandler(HttpServletRequest request) {
        Map<String, String> templateVariables = new HashMap<>();
        String method = request.getMethod();
        String path = PathUtils.canonicalize(request);
        HandlerWrapper handlerWrapper = RequestMappingFactory.getHandlerWrapper(method, path,templateVariables);
        List<Interceptor> interceptorList = AopFactory.getInterceptors(path);
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain(handlerWrapper, interceptorList);
        if(!ObjectUtils.isEmpty(templateVariables)){
            handlerExecutionChain.setTemplateVariables(templateVariables);
        }
        return handlerExecutionChain;
    }

    /**
     * 清理资源
     */
    public void destroy() {
        JettyServer.stop();
    }
}
