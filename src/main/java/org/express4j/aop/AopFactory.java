package org.express4j.aop;

import org.express4j.utils.PathMatchUtils;

import java.util.*;

/**
 * Created by Song on 2015/12/13.
 */
public class AopFactory {

    private static Map<String,List<Class<? extends Interceptor>>> interceptorMap = new HashMap<>();

    /**
     * 将路径与拦截器
     * @param path
     * @param interceptors
     */
    public static void addMapping(String path,Class<? extends Interceptor> ... interceptors){
        if(!interceptorMap.containsKey(path)){
            List<Class<? extends Interceptor>> interceptorList = new ArrayList<>();
            addInterceptorToList(interceptorList, interceptors);
            interceptorMap.put(path, interceptorList);
        }else {
            List<Class<? extends Interceptor>> interceptorList = interceptorMap.get(path);
            addInterceptorToList(interceptorList,interceptors);
        }
    }

    /**
     * 根据路径获取对应的拦截器集合
     * @param path
     * @return
     */
    public static List<Interceptor> getInterceptors(String path){
        List<Interceptor> interceptorList = new LinkedList<>();
        for(String templatePath : interceptorMap.keySet()){
            if(PathMatchUtils.matches(templatePath,path)){
                path = templatePath;
            }
        }
        List<Class<? extends Interceptor>> intercetorsClasses = interceptorMap.get(path);
        if (intercetorsClasses!=null) {
            for(Class<? extends Interceptor> interceptorCLass : intercetorsClasses){
                try {
                    Interceptor interceptor  = interceptorCLass.newInstance();
                    interceptorList.add(interceptor);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return interceptorList;
        }
        return null;
    }

    /**
     * 将拦截器添加到列表中
     * @param interceptorList
     * @param interceptors
     */
    private static void addInterceptorToList(List<Class<? extends Interceptor>> interceptorList, Class<? extends Interceptor>[] interceptors) {
        for(Class<? extends Interceptor> interceptor : interceptors){
            interceptorList.add(interceptor);
        }
    }
}
