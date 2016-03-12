package org.express4j.http.mapping;

import org.express4j.handler.Handler;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.express4j.utils.ConvertUtils;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Song on 2015/12/25.
 */
public class HandlerWrapper {

    private Class<?> cls;

    private Handler handler;

    private Method method;

    private LinkedList<MethodParamWrapper> params = null;

    private HandlerType type ;

    public void setHandler(Handler handler){
        this.cls = cls;
        this.handler = handler;
        this.type = HandlerType.Handler;
    }

    public void setHandler(Class<?> cls ,Method method){
        this.cls = cls;
        this.method = method;
        this.type = HandlerType.Method;
    }
    public void setHandler(Class<?> cls ,Method method,LinkedList<MethodParamWrapper> params){
        this.cls = cls;
        this.method = method;
        this.params = params;
        this.type = HandlerType.Method;
    }

    public void invoke(Request request,Response response) throws Exception{
        if(type.equals(HandlerType.Handler)){
            handler.handle(request, response);
        }
        if(type.equals(HandlerType.Method)){
            Object target = cls.newInstance();
            Map<String,Object> paramMap = request.params();
            Object[] paramArray = new Object[paramMap.size()];
            int index = 0;
            if (paramMap!=null && !paramMap.isEmpty()) {
                for (MethodParamWrapper param : params) {
                    Object value =paramMap.get(param.getParamName());
                    paramArray[index++] = ConvertUtils.convert(param.getParamType(),value);
                }
            }
            method.setAccessible(true);
            Handler handler = (Handler) method.invoke(target,paramArray);
            handler.handle(request, response);
        }
    }


    public enum HandlerType{
        Handler,Method
    }

}
