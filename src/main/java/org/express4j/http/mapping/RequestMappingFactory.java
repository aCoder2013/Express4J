package org.express4j.http.mapping;

import org.express4j.handler.Handler;
import org.express4j.utils.AntPathMatcher;
import org.express4j.utils.PathMatcher;
import org.express4j.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 封装HTTP请求匹配信息
 * Created by Song on 2015/12/4.
 */
public class RequestMappingFactory {

    private static Map<RequestMapping, HandlerWrapper> regularHandlerMap = new HashMap<>();

    private static PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 增加路由匹配信息
     * @param method 请求方法
     * @param path  请求路径
     * @param handler 处理器
     */
    public static void addMapping(String method, String path, Handler handler) {
        if(StringUtils.isEmpty(method) || StringUtils.isEmpty(path) || handler == null){
            return;
        }
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
        HandlerWrapper wrapper = new HandlerWrapper();
        wrapper.setHandler(handler);
        regularHandlerMap.put(new RequestMapping(method, path), wrapper);
    }

    /**
     * 增加路由匹配信息
     * @param method 请求方法
     * @param path  请求路径
     * @param handler 处理器
     */
    public static void addMapping(String method, String path, Class<?> cls ,Method handler,LinkedList<MethodParamWrapper> params) {
        if(StringUtils.isEmpty(method) || StringUtils.isEmpty(path) || handler == null){
            return;
        }

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
        HandlerWrapper wrapper = new HandlerWrapper();
        wrapper.setHandler(cls,handler,params);
        regularHandlerMap.put(new RequestMapping(method, path), wrapper);
    }

    /**
     * 根据请求方法和路径得到对应的控制器
     * @param method HTTP方法
     * @param path  HTTP路径
     * @param templateVariables 保存模板变量
     *        例如：/person/{id} => /person/123 = {id:123}
     * @return
     */
    public static HandlerWrapper getHandlerWrapper(String method, String path,Map<String, String> templateVariables) {
        List<String> matchedPath = new ArrayList<>();
        if(regularHandlerMap != null){
            for (Map.Entry<RequestMapping, HandlerWrapper> entries : regularHandlerMap.entrySet()) {
                RequestMapping mapping = entries.getKey();
                if (mapping.getMethod().equals(method.toUpperCase())) {
                    if (pathMatcher.match(mapping.getPathPattern(), path)) {
                        matchedPath.add(mapping.getPathPattern());
                    }
                }
            }
        }

        if (!matchedPath.isEmpty()) {
            String bestPath = "";

            if(matchedPath.size() == 1){
                bestPath = matchedPath.get(0);
            }else {
                for(String p : matchedPath){
                    if(p.length()==bestPath.length()){
                        //todo 简单计算通配符数量，过于简单
                        if(StringUtils.compute(p, '*')<StringUtils.compute(bestPath,'*')){
                            bestPath = p;
                        }
                    }
                    if(bestPath.length()<p.length()){
                        bestPath = p;
                    }
                }
            }
            templateVariables.putAll(pathMatcher.extractUriTemplateVariables(bestPath,path));
            return regularHandlerMap.get(new RequestMapping(method,bestPath));
        }
        return null;
    }


/*    public static Handler getHandlerWrapper(String method, String path){
        Handler handler = null;
        handler = regularHandlerMap.entrySet().stream()
                .filter(entry -> {
                    if(entry.getKey().getMethod().equals(method.toUpperCase()) && RegUtils.matches(entry.getKey().getPathPattern(), path)){
                        return true;
                    }
                    return false;
                }).findAny().get().getValue();
        return handler;
    }*/
}
