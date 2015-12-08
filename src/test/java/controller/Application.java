package controller;

import model.User;
import org.express4j.http.HttpStatusCode;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.express4j.core.Express4J.*;

/**
 * Created by Song on 2015/12/7.
 */
public class Application {


    @BeforeClass
    public static void testGet() {
        get("/hello", (request, response) ->
                        response.renderHtml("<h1>Hello World</h1>")
        );

        get("/error", (request, response) ->
                response.status(HttpStatusCode.BAD_REQUEST).renderText("Bad Request !"));

        get("/testjson", (request, response) ->
                        response.json(new User("Mars", 22, "123456789"))
        );

        get("/cookietest", (request, response) -> {
            System.out.println(request.cookies());
            response.renderHtml("<h1>" + request.cookie("blog") + "</h1>");
        });

        get("/setcookie", (request, response) -> {
            response.cookie("dznews", "interesting");
            response.status(HttpStatusCode.OK).renderHtml("Success!");
        });

        get("/index", (request, response) -> {
            response.set("name", "小明");
            response.send("index.ftl");
        });
        serverPort(9000);
        run();
        setBaseUrl("http://localhost:9000");
    }

    @Test
    public void html() {
        beginAt("/hello");
        assertTextPresent("Hello World");
    }

    @Test
    public void json() {
        beginAt("/testjson");
        assertResponseCode(200);
        assertTextPresent("{\"name\":\"Mars\",\"age\":22,\"password\":\"123456789\"}");
    }

    @Test
    public void freemarker() {
        beginAt("/index");
        assertTitleEquals("Express4J");
        assertTextPresent("Hello 小明");
    }


}