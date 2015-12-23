package org.express4j.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.express4j.core.CoreFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by Song on 2015/12/5.
 */
public class JettyServer {

    private static final Server server = new Server();

    /**
     * 默认服务器监听的端口
     */
    public static  int SERVER_PORT = 8080;

    /**
     * 设置端口
     * @param port
     */
    public static void setServerPort(int port){
        SERVER_PORT = port;
    }

    /**
     * 启动服务器
     */
    public static void start(){
        Thread startJetty = new Thread(() -> initServer(SERVER_PORT));
        startJetty.start();
    }

    /**
     * 停止服务器
     */
    public static void stop(){
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化服务器
     */
    private static void initServer(int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addFilter(CoreFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(contextHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
