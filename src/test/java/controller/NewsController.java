package controller;

import org.express4j.annotation.RequestMapping;
import org.express4j.handler.Handler;

/**
 * Created by Song on 2015/12/13.
 */
public class NewsController {


    @RequestMapping("/lists")
    public Handler list() {
        return (request, response) ->
                response.renderText("List News");
    }
}
