package org.express4j.handler;

import org.express4j.aop.Interceptor;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.express4j.http.mapping.HandlerWrapper;
import org.express4j.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song on 16-3-20.
 */
public class HandlerExecutionChain {

    private static final Logger logger = LoggerFactory.getLogger(HandlerExecutionChain.class);

    private final HandlerWrapper handlerWrapper;

    private Interceptor[] interceptors;

    private List<Interceptor> interceptorList;

    /**
     * 保存路径中的模板变量
     * 例如：/person/{id} => /person/123 = {id:123}
     */
    private Map<String, String> templateVariables;


    public HandlerExecutionChain(HandlerWrapper handlerWrapper) {
        this(handlerWrapper,null);
    }

    public HandlerExecutionChain(HandlerWrapper handlerWrapper,List<Interceptor> interceptorList) {
        this.handlerWrapper = handlerWrapper;
        this.interceptorList = interceptorList;
    }

    public Map<String, String> getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(Map<String, String> templateVariables) {
        this.templateVariables = templateVariables;
    }

    public HandlerWrapper getHandlerWrapper() {
        return handlerWrapper;
    }

    public Handler getHandler() {
        return handlerWrapper.getHandler();
    }

    private List<Interceptor> getInterceptorList(){
        if(this.interceptorList == null){
            interceptorList = new ArrayList<>();
            interceptors = null;
        }
        return interceptorList;
    }


    public void addInterceptor(Interceptor interceptor){
        if(!ObjectUtils.isEmpty(interceptor)){
            getInterceptorList().add(interceptor);
        }
    }

    public void addInterceptors(Interceptor ... interceptors){
        if(!ObjectUtils.isEmpty(interceptors)){
            getInterceptorList().addAll(Arrays.asList(interceptors));
        }
    }

    public Interceptor[] getInterceptors(){
        if(interceptors == null && interceptorList != null){
            interceptors = interceptorList.toArray(new Interceptor[interceptorList.size()]);
        }
        return interceptors;
    }

    public void handle(HttpServletRequest request,HttpServletResponse response) throws Exception {
      if(!applyPreHandle(request, response)){
        return;
      }
      invoke(request, response);
      applyAfterHandler(request, response);
    }



    /**
     * 执行前置拦截器
     * @param request
     * @param response
     * @return
     */
    public boolean applyPreHandle(HttpServletRequest request,HttpServletResponse response){
        Interceptor[] interceptors = getInterceptors();
        if(!ObjectUtils.isEmpty(interceptors)){
            for(int i =0 ;i < interceptors.length;i++){
                if(!interceptors[i].before(request,response,getHandler())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 调用处理器方法
     * @param request
     * @param response
     * @throws Exception
     */
    public void invoke(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Request req = new Request(request);
        Response res = new Response(response);
        if(!ObjectUtils.isEmpty(this.templateVariables)){
            req.addPathVariables(this.templateVariables);
        }
        handlerWrapper.invoke(req, res);
    }

    /**
     * 执行后置拦截器
     * @param request
     * @param response
     * @return
     */
    public void applyAfterHandler(HttpServletRequest request,HttpServletResponse response){
        Interceptor[] interceptors = getInterceptors();
        if(!ObjectUtils.isEmpty(interceptors)){
            for(int i = interceptors.length-1;i>=0;i--){
                interceptors[i].after(request, response, getHandler());
            }

        }
    }


}

