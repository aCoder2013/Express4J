package org.express4j.core;

import org.express4j.handler.Handler;
import org.express4j.http.mapping.RequestMappingFactory;
import org.express4j.webserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Song on 2015/12/4.
 */
public final class Express4J{


    private Logger logger = LoggerFactory.getLogger(Express4J.class);
    
    private static final Express4J INSTANCE = new Express4J();





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
        return INSTANCE;
    }

    /**
     * 匹配HTTP GET 请求
     *
     * @param path
     * @param handler
     */
    public static void get(String path, Handler handler) {
        RequestMappingFactory.addMapping("GET", path, handler);
    }

    /**
     * 匹配HTTP POST 请求
     *
     * @param path
     * @param handler
     */
    public static void post(String path, Handler handler) {
        RequestMappingFactory.addMapping("POST", path, handler);
    }


    /**
     * 匹配HTTP PUT 请求
     *
     * @param path
     * @param handler
     */
    public static void put(String path, Handler handler) {
        RequestMappingFactory.addMapping("PUT", path, handler);
    }

    /**
     * 匹配HTTP DELETE 请求
     *
     * @param path
     * @param handler
     */
    public static void delete(String path, Handler handler) {
        RequestMappingFactory.addMapping("DELETE", path, handler);
    }



    /**
     * 启动应用
     */
    public static void run(){
        JettyServer.setServerPort(Express4JConfig.getServerPort());
        JettyServer.start();
    }

    /**
     * 停止应用
     */
    public static void stop(){
        JettyServer.stop();
    }


}
