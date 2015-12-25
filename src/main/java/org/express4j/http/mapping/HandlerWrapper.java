package org.express4j.http.mapping;

import org.express4j.handler.Handler;
import org.express4j.http.Request;
import org.express4j.http.Response;

import java.lang.reflect.Method;

/**
 * Created by Song on 2015/12/25.
 */
public class HandlerWrapper {

    private Class<?> cls;

    private Handler handler;

    private Method method;

    private HandlerType type ;

    public void setHandler(Handler handler){
        this.cls = cls;
        this.handler = handler;
        this.type = HandlerType.Handler;
    }

    public void setHandler(Class<?> cls ,Method method){
        this.cls = cls;
        this.method = method;
    }


    public void invoke(Request request,Response response) throws Exception{
        if(type.equals(HandlerType.Handler)){
            handler.handle(request, response);
        }
    }


    public enum HandlerType{
        Handler,Method
    }

}
