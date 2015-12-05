package org.express4j.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by Song on 2015/12/4.
 */
public class Express4JServer {

    public static void start(){
        Server server = new Server(8080);
        WebAppContext webApp = new WebAppContext();
        webApp.setThrowUnavailableOnStartupException(true);	// 在启动过程中允许抛出异常终止启动并退出 JVM
        webApp.setContextPath("/");
        webApp.setResourceBase(".");
        server.setHandler(webApp);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
