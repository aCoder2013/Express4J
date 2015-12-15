package controller;

import static org.express4j.core.Express4J.*;

/**
 * Created by Song on 2015/12/14.
 */
public class HelloWorld {

    public static void main(String[] args) {
        get("/hello",(req, res) ->res.renderHtml("Hello World"));
        run();
    }
}
