package org.express4j.handler;

import org.express4j.core.ExceptionHandlerFactory;
import org.express4j.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song on 16-3-20.
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {


    @Override
    public void resolveException(HttpServletRequest request, HttpServletResponse response, Handler handler, Exception e) {
        Object result = null;
        // TODO: 16-3-20 增加子类的支持
        ExceptionHandlerWrapper handlerWrapper = ExceptionHandlerFactory.getExceptionHandlerWrapper(e.getClass());
        if (handlerWrapper == null) {
            //如果没有则回退到Exception处理器
            handlerWrapper = ExceptionHandlerFactory.getHandlerWrapperMap().get(Exception.class);
        }
        if (handlerWrapper != null) {

            try {
                Object obj = handlerWrapper.getaClass().newInstance();
                Method method = handlerWrapper.getaMethod();
                int code = handlerWrapper.getStatusCode();
                //构造方法参数
                List<?> parameters = constructParameters(request,response,e, method);
                result = method.invoke(obj, parameters.toArray());
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
        } else {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造方法参数
     *
     * @param e
     * @param method
     * @return
     */
    private List<?> constructParameters(HttpServletRequest request,HttpServletResponse response,Exception e, Method method) {
        List paramList = new ArrayList<>();
        for (Class<?> cls : method.getParameterTypes()) {
            if (HttpServletRequest.class.isAssignableFrom(cls)) {
                paramList.add(request);
            } else if (HttpServletResponse.class.isAssignableFrom(cls)) {
                paramList.add(response);
            } else if (Exception.class.isAssignableFrom(cls)) {
                paramList.add(e);
            }
        }
        return paramList;
    }
}
