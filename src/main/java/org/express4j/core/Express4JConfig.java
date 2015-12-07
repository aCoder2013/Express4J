package org.express4j.core;


import org.express4j.utils.ClassUtils;

/**
 * Created by Song on 2015/12/4.
 */
public class Express4JConfig {


    /**
     * 默认静态文件路径
     */
    public static final String DEFAULT_STATIC_PATH = "static";

    /**
     * 默认模板文件路径
     */
    public static final String DEFAULT_TEMPLATE_PATH = "templates";



    /**
     * 静态文件路径
     */
    public static String staticFilePath ;

    /**
     * 模板文件存放路径
     */
    public static String templatesPath;

    /**
     * 服务器监听端口
     */
    public static int serverPort ;

    /**
     * 默认监听端口
     */
    private static final int DEFAULT_SERVER_PORT = 8080;

    /**
     * 初始化默认配置
     */
    static {
        staticFilePath = ClassUtils.getResourcePath(DEFAULT_STATIC_PATH);
        templatesPath  = ClassUtils.getResourcePath(DEFAULT_TEMPLATE_PATH);
        serverPort = DEFAULT_SERVER_PORT;
    }

    public static String getStaticFilePath() {
        return staticFilePath;
    }

    public static void setStaticFilePath(String staticFilePath) {
        Express4JConfig.staticFilePath = staticFilePath;
    }

    public static String getTemplatesPath() {
        return templatesPath;
    }

    public static void setTemplatesPath(String templatesPath) {
        Express4JConfig.templatesPath = templatesPath;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        Express4JConfig.serverPort = serverPort;
    }
}
