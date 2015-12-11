package org.express4j.http.mapping;

import org.express4j.handler.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class RequestMappingFactory {

    private static Map<RequestMapping,Handler> handlerMap = new HashMap<>();

    public static void addMapping(String method, String path, Handler handler) {
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
        handlerMap.put(new RequestMapping(method, path),handler);
    }

    public static Handler getHandler(String method, String path){
        method = method.toUpperCase();
        return handlerMap.get(new RequestMapping(method, path));
    }
}
