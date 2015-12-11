package org.express4j.core;

import org.express4j.handler.Handler;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.multipart.FileUploadHelper;
import org.express4j.utils.JsonUtils;
import org.express4j.webserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

    private InterceptorScanner interceptorScanner = new InterceptorScanner();

    private HttpServletRequest request ;

    private HttpServletResponse response;

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CoreFilter Init");
        servletContext = filterConfig.getServletContext();
        FileUploadHelper.init(servletContext);
        interceptorScanner.init();
    }




    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //向下转型
        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;
        //设置编码
        request.setCharacterEncoding(DEFAULT_CHARSET);
        response.setCharacterEncoding(DEFAULT_CHARSET);

        if(request.getRequestURI().equals("/favicon.ico")){
            return;
        }
        String path = getPath(request);
        Handler handler = RequestMappingFactory.getHandler(request.getMethod(), path);
            if (handler!=null) {
                try {
                    handler.handle(new Request(request),new Response(response));
                } catch (Exception e) {
                    handleException(e);
                }
            }
    }

    /**
     * 处理异常
     * @param e
     */
    private void handleException(Exception e) {
        Object result = null;
        if (interceptorScanner.getMethodMap().containsKey(e.getClass())) {
            try {
                InterceptorScanner.ExceptionHandlerWrapper handlerWrapper = interceptorScanner.getMethodMap().get(e.getClass());
                Object obj = handlerWrapper.getaClass().newInstance();
                Method method = handlerWrapper.getaMethod();
                int code = handlerWrapper.getStatusCode();
                List<?> parameters  = constructParameters(e, method);
                result = method.invoke(obj,parameters.toArray());
                response.sendError(code, JsonUtils.toJson(result));
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }else {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造方法参数
     * @param e
     * @param method
     * @return
     */
    private List<?> constructParameters(Exception e, Method method) {
        List paramList = new ArrayList<>();
        for(Class<?> cls :method.getParameterTypes()){
            if(HttpServletRequest.class.isAssignableFrom(cls)){
                paramList.add(request);
            }else if(HttpServletResponse.class.isAssignableFrom(cls)){
                paramList.add(response);
            }else if (Exception.class.isAssignableFrom(cls)){
                paramList.add(e);
            }
        }
        return paramList;
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
