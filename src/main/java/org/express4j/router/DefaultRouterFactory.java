package org.express4j.router;

import org.express4j.handler.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class DefaultRouterFactory{

    private static Map<Router,Handler> handlerMap = new HashMap<>();

    public static void addRouter(String method, String path, Handler handler) {
        if(!path.startsWith("/")){
            return;
        }
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-1);
        }
        if(handler==null){
            return;
        }
        method = method.toUpperCase();//转换成大写
        handlerMap.put(new Router(method, path),handler);
    }

    public static Handler getHandler(String method, String path){
        method = method.toUpperCase();
        return handlerMap.get(new Router(method, path));
    }
}
