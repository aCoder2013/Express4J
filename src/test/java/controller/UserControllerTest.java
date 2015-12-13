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
public class UserControllerTest {


    @BeforeClass
    public static void init(){
        Express4J.route("/news").with(NewsController.class);
        Express4J.route("/user").with(UserController.class);
        setBaseUrl("http://localhost:8080");
        Express4J.run();
    }

    @Test
    public void list(){
        beginAt("/news/list");
        assertTextPresent("List News");
    }

    @Test
    public void login(){
        beginAt("/user/login");
        assertTextPresent("Hello UserController");
    }

    @Test
    public void register(){
        beginAt("/user/register");
        assertTextPresent("Register");
    }
}
