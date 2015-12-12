package org.express4j.http.mapping;

import org.express4j.handler.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class RequestMappingFactory {

    private static Map<RequestMapping,Handler> regularHandlerMap = new HashMap<>();


    public static void addMapping(String method, String path, Handler handler) {
        if(!path.startsWith("/")){
            return;
        }
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-1);
        }
        if(path.contains(":")){
            int colonIndex = path.indexOf(":");
            int salash  = path.indexOf(colonIndex,'/');
        }
        if(handler==null){
            return;
        }
        method = method.toUpperCase();//转换成大写
        regularHandlerMap.put(new RequestMapping(method, path), handler);
    }

    public static Handler getHandler(String method,String path){
        for(Map.Entry<RequestMapping,Handler> entries :regularHandlerMap.entrySet()){
            RequestMapping mapping = entries.getKey();
            if(mapping.getMethod().equals(method.toUpperCase())){
                if(matches(mapping.getPath(),path)){
                    return entries.getValue();
                }
            }
        }
        return null;
    }

    private static boolean matches(String template,String path) {
        String[] templatePathList =template.split("/");
        String[] pathList=path.split("/");
        if(templatePathList.length==pathList.length){
            int length = templatePathList.length;
            int i = 0;
            while(i<length){
                String partPath = pathList[i];
                String partTemplatePath = templatePathList[i];
                if(!partPath.equals(partTemplatePath) && !partTemplatePath.startsWith(":")){
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
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
