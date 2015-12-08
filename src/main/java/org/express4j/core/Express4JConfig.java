package org.express4j.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Song on 2015/12/4.
 */
public class Express4JConfig {

    private static final Logger logger = LoggerFactory.getLogger(Express4JConfig.class);

    /**
     * 静态文件路径
     */
    public static String staticFilePath ="static";

    /**
     * 模板文件存放路径
     */
    public static String templatesPath = "templates";

    /**
     * 服务器监听端口
     */
    public static int serverPort  = 8080;


    /**
     * 最大文件上传容量
     * @return
     */
    private static int maxFileSize = 10*1024*1024;


    public static String getStaticFilePath() {
        return staticFilePath;
    }

    public static void setStaticFilePath(String staticFilePath) {
        logger.info("Set static file path to :" + staticFilePath);
        Express4JConfig.staticFilePath = staticFilePath;
    }

    public static String getTemplatesPath() {
        return templatesPath;
    }

    public static void setTemplatesPath(String templatesPath) {
        logger.info("Set templates path to :" + templatesPath);
        Express4JConfig.templatesPath = templatesPath;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        Express4JConfig.serverPort = serverPort;
    }


    public static int getMaxFileSize() {
        return maxFileSize;
    }

    public static void setMaxFileSize(int maxFileSize) {
        logger.info("Set max file upload size  to :" + maxFileSize);
        Express4JConfig.maxFileSize = maxFileSize;
    }
}
