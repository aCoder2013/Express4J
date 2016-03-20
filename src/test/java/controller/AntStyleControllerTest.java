package controller;

import org.express4j.core.Express4J;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/**
 * Created by Song on 2016/3/13.
 */
public class AntStyleControllerTest {

    @BeforeClass
    public static void setUp(){
        Express4J.get("/news/{id}",(request, response) -> {
            response.renderText(request.pathParam("id"));
        });

        Express4J.get("/**/a",(request, response) ->{
            System.out.println(request.baseUrl());
            response.renderText(request.baseUrl());
        });
        setBaseUrl("http://localhost:8080");
    }

    @Test
    public void test(){
        beginAt("/news/123");
        assertTextPresent("123");
    }

    @Test
    public void testDoubleStar(){
        beginAt("/topic/comment/a");
        assertTextPresent("/topic/comment/a");
    }


}
