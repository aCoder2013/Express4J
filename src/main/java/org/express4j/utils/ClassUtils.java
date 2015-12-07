package org.express4j.utils;

/**
 * Created by Song on 2015/12/7.
 */
public class ClassUtils {


    /**
     * 获取ClassPath中指定名称资源的路径
     * @param name the name of resource
     * @return the path of resource
     */
    public static String getResourcePath(String name){
       return Thread.currentThread()
               .getContextClassLoader()
               .getResource(name)
               .getPath();
    }
}
