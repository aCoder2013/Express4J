package controller;

import org.express4j.annotation.RequestMapping;
import org.express4j.handler.Handler;
import org.express4j.http.HttpMethod;

/**
 * Created by Song on 2015/12/13.
 */
public class UserController {



    @RequestMapping(value = "/login",method = HttpMethod.GET)
    private Handler login() {
        return (request, response) -> {
            response.renderHtml("Hello UserController");
        };
    }

    @RequestMapping("/register")
    public Handler register(){
        return (request, response) -> response.renderHtml("<h2>Register</h2>");
    }
}
