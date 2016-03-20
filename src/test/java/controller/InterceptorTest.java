package controller;

import org.express4j.aop.Interceptor;
import org.express4j.core.Express4J;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/**
 * Created by Song on 2015/12/13.
 */
public class InterceptorTest {

    @BeforeClass
    public static void init(){

        Express4J.get("/list/detail/*",(request1, response1) ->
                response1.renderText("list/detail/*")
        );

        Express4J.addInterceptor("/list/*",
                LoginInterceptor.class,TestLoginInterceptor.class);
        Express4J.get("/list/*", (request, response) ->
                response.renderText("/list/*")
        );

        Express4J.get("/*/*",(request, response) -> {
            response.renderText("/*/*");
        });
        Express4J.get("/list/1", (request, response) ->{
            response.renderHtml("/list/1");
        });
        setBaseUrl("http://localhost:8080");
    }


    @Test
    public void testConflict(){
        beginAt("/list/detail/1");
        assertTextPresent("list/detail/*");
    }

    @Test
    public void test1(){
        beginAt("/list/1");
        assertTextPresent("/list/1");
    }

    public static class TestLoginInterceptor implements Interceptor{

        @Override
        public boolean before(Request request, Response response, Object handler) {
            response.renderText("Before TestLoginInterceptor");
            return false;
        }

        @Override
        public boolean after(Request request, Response response, Object handler) {
            response.renderText("After TestLoginInterceptor");
            return false;
        }
    }



    public static class LoginInterceptor implements Interceptor{

        @Override
        public boolean before(Request request, Response response, Object handler) {
            response.renderText("before");
            return true;
        }

        @Override
        public boolean after(Request request, Response response, Object handler) {
            response.renderText("after");
            return true;
        }
    }
}
