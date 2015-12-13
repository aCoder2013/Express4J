package org.express4j.http.mapping;

import org.express4j.handler.Handler;
import org.express4j.utils.PathMatchUtils;
import org.express4j.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class RequestMappingFactory {

    private static Map<RequestMapping, Handler> regularHandlerMap = new HashMap<>();


    public static void addMapping(String method, String path, Handler handler) {
        if (!path.startsWith("/")) {
            return;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (handler == null) {
            return;
        }
        method = method.toUpperCase();//转换成大写
        regularHandlerMap.put(new RequestMapping(method, path), handler);
    }

    public static Handler getHandler(String method, String path) {
        List<String> matchedPath = new ArrayList<>();
        for (Map.Entry<RequestMapping, Handler> entries : regularHandlerMap.entrySet()) {
            RequestMapping mapping = entries.getKey();
            if (mapping.getMethod().equals(method.toUpperCase())) {
                if (PathMatchUtils.matches(mapping.getPath(), path)) {
                    matchedPath.add(mapping.getPath());
                }
            }
        }
        if (!matchedPath.isEmpty()) {
            String bestPath = "";
            for(String p : matchedPath){
                if(p.length()==bestPath.length()){
                    if(StringUtils.compute(p,'*')<StringUtils.compute(bestPath,'*')){
                        bestPath = p;
                    }
                }
                if(bestPath.length()<p.length()){
                    bestPath = p;
                }
            }
            return regularHandlerMap.get(new RequestMapping(method,bestPath));
        }
        return null;
    }


/*    public static Handler getHandler(String method, String path){
        Handler handler = null;
        handler = regularHandlerMap.entrySet().stream()
                .filter(entry -> {
                    if(entry.getKey().getMethod().equals(method.toUpperCase()) && RegUtils.matches(entry.getKey().getPath(), path)){
                        return true;
                    }
                    return false;
                }).findAny().get().getValue();
        return handler;
    }*/
}
