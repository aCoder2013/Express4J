package controller;

import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.express4j.core.Express4J.controller;

/**
 * Created by Song on 2015/12/13.
 */
public class UserControllerTest {


    @BeforeClass
    public static void init(){
        controller("/news").with(NewsController.class);
        controller("/user").with(UserController.class);
        setBaseUrl("http://localhost:8080");
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
