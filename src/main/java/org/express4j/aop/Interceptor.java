package org.express4j.aop;

import org.express4j.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 供使用者实现
 * Created by Song on 2015/12/13.
 */
public interface Interceptor {


    /**
     * 在处理器Handler执行之前调用，Express4J可以同时存在多个拦截器
     * 将会根据声明顺序进行依次调用，也可以中断执行
     * @param request HttpServletRequest 包装类
     * @param response HttpServletResponse 包装类
     * @param handler  处理器
     * @return true:如果想继续拦截器链的执行，false:想要终止处理
     */
    boolean before(HttpServletRequest request, HttpServletResponse response, Handler handler);


    /**
     * 在处理器Handler执行之后调用，Express4J可以同时存在多个拦截器
     * 将会根据声明顺序进行依次调用，也可以中断执行
     * @param request HttpServletRequest 包装类
     * @param response HttpServletResponse 包装类
     * @param handler  处理器
     * @return true:如果想继续拦截器链的执行，false:想要终止处理
     */
    boolean after(HttpServletRequest request,HttpServletResponse response,Handler handler);

}
