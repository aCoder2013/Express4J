package controller;

import org.express4j.aop.Interceptor;
import org.express4j.core.Express4J;
import org.express4j.http.Request;
import org.express4j.http.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;

/**
 * Created by Song on 2015/12/13.
 */
public class InterceptorTest {

    @BeforeClass
    public static void init(){

        Express4J.get("/list/detail/*",(request1, response1) ->
                System.out.println("list/detail/*")
        );

        Express4J.addInterceptor("/list/*", LoginInterceptor.class);
        Express4J.get("/list/*", (request, response) ->
                System.out.println("list/*")
        );

        Express4J.get("/*/*",(request1, response1) -> {
            System.out.println("/*/*");
        });
        Express4J.get("/list/1", (request, response) ->{
            System.out.println("Hello1");
            response.renderHtml("Hello1");
        });
        Express4J.get("/list/2", (request, response) ->
                System.out.println("Hello2")
        );
        Express4J.get("/list/3", (request, response) ->
                        System.out.println("Hello3")
        );
        setBaseUrl("http://localhost:8080");
        Express4J.run();
    }
    @Test
    public void testConflict(){
        beginAt("/list/detail/1");
    }

    @Test
    public void test1(){
        beginAt("/list/1");
    }


    @Test
    public void test2(){
        beginAt("/list/2");
    }

    @Test
    public void test3(){
        beginAt("/list/3");
    }




    public static class LoginInterceptor implements Interceptor{

        @Override
        public boolean before(Request request, Response response, Object handler) {
            System.out.println("Before");
            return false;
        }

        @Override
        public boolean after(Request request, Response response, Object handler) {
            System.out.println("After");
            return false;
        }
    }
}
