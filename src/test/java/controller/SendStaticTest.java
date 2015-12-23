package controller;

import org.express4j.core.Express4J;

/**
 * 测试发送静态文件
 * Created by Song on 2015/12/23.
 */
public class SendStaticTest {

    public static void main(String[] args) {
        Express4J.get("/static",(request, response) ->
            response.sendStaticFile("js/jquery-2.1.3.min.js")
        );
    }
}
