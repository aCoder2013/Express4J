package org.express4j.core;

import org.express4j.annotation.RequestMapping;
import org.express4j.aop.AopFactory;
import org.express4j.aop.Interceptor;
import org.express4j.handler.Handler;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.webserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Song on 2015/12/4.
 */
public final class Express4J{


    private Logger logger = LoggerFactory.getLogger(Express4J.class);
    
    private static final Express4J INSTANCE = new Express4J();

    private static String prefixPath = "";

    private static boolean isRunning = false;



    /**
     * 只能存在一个实例
     */
    private Express4J(){
    }

    /**
     * 获取实例
     * @return
     */
    public static Express4J getInstance(){
        return INSTANCE;
    }
    /**
     * 设置静态文件路径
     * 比如 :CSS/JS/IMG
     * @param path
     */
    public static void staticFilePath(String path){
        Express4JConfig.setStaticFilePath(path);
    }

    /**
     * 设置模板文件路径
     * 比如 : JSP/FTL
     * @param path
     */
    public static void templatePath(String path){
        Express4JConfig.setTemplatesPath(path);
    }

    /**
     * 设置服务器监听端口
     * @param port
     */
    public static Express4J listen(int port){
        Express4JConfig.setServerPort(port);
        if(isRunning()){
            restart();
        }
        return INSTANCE;
    }




    /**
     * 向指定路径添加拦截器
     * @param path
     * @param interceptor
     */
    public static void addInterceptor(String path,Class<? extends Interceptor> ... interceptor){
        AopFactory.addMapping(path,interceptor);
    }


    /**
     *  设置路由前缀
     * @param prefix
     * @return
     */
    public static Express4J controller(String prefix){
        prefixPath = prefix;
        return Express4J.getInstance();
    }

    /**
     * 设置要解析的控制器
     * 设置路由前缀可以调用{@link Express4J#controller}
     * @param cls
     * @return
     */
    public static Express4J with(Class<?> cls){
        parseHandler(cls);
        return getInstance();
    }

    /**
     * 解析给定Class中的所有方法
     * @param cls
     */
    private static void parseHandler(Class<?> cls) {
        try {
            Object instance = cls.newInstance();
            Method[] methods = cls.getMethods();
            for(Method method :methods){
                if(method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping pathClass = method.getAnnotation(RequestMapping.class);
                    String path = pathClass.value();//得到路径
                    String httpMethod = pathClass.method().name();
                    Handler handler = (Handler) method.invoke(instance);
                    RequestMappingFactory.addMapping(httpMethod, prefixPath+path, handler);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 匹配HTTP GET 请求
     *
     * @param path
     * @param handler
     */
    public static void get(String path, Handler handler) {
        ensureServerRunning();
        RequestMappingFactory.addMapping("GET", path, handler);
    }

    /**
     * 匹配HTTP POST 请求
     *
     * @param path
     * @param handler
     */
    public static void post(String path, Handler handler) {
        ensureServerRunning();
        RequestMappingFactory.addMapping("POST", path, handler);
    }

    /**
     * 匹配HTTP PUT 请求
     *
     * @param path
     * @param handler
     */
    public static void put(String path, Handler handler) {
        ensureServerRunning();
        RequestMappingFactory.addMapping("PUT", path, handler);
    }


    /**
     * 匹配HTTP DELETE 请求
     *
     * @param path
     * @param handler
     */
    public static void delete(String path, Handler handler) {
        ensureServerRunning();
        RequestMappingFactory.addMapping("DELETE", path, handler);
    }

    /**
     * 检查服务器是否在运行状态
     * @return
     */
    private static boolean isRunning(){
        return isRunning;
    }

    /**
     * 确保服务器保持在运行状态
     */
    private static void ensureServerRunning() {
        if(!isRunning()){
            run();
        }
    }

    /**
     * 启动应用
     */
    public static void run(){
        if(!isRunning()){
            JettyServer.setServerPort(Express4JConfig.getServerPort());
            JettyServer.start();
            isRunning = true;
        }
    }

    /**
     * 停止应用
     */
    public static void stop(){
        JettyServer.stop();
    }

    /**
     * 重新启动服务器
     */
    private static void restart() {
        stop();
        run();
    }

}
