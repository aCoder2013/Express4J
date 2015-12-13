package controller;

import org.express4j.core.Express4J;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;

/**
 * Created by Song on 2015/12/13.
 */
public class UrlWithStarTest {


    @BeforeClass
    public static void init(){
        Express4J.get("/hello/*/to/*",(request, response) ->
            response.renderHtml("Hello "+request.pathParam("0")+" To "+request.pathParam("1"))
        );
        setBaseUrl("http://localhost:8080");
        Express4J.run();
    }

    @Test
    public void test(){
        beginAt("/hello/Mars/to/Tom");
        assertTextPresent("Hello Mars To Tom");
    }
}
