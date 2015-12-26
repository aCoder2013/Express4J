package controller;

import org.express4j.core.Express4J;
import org.express4j.handler.Handler;

/**
 * Created by Song on 2015/12/24.
 */
public class HelloRoute {

    public Handler hello(Boolean id,String name){
        return (request, response) ->
                response.renderText(id+":"+name);
    }

    public Handler world(){
        return (request, response) ->{
            response.set("name", "小明");
            response.send("index.ftl");
        };
    }

    public static void main(String[] args) {
        Express4J.run();
    }
}
