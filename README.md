# Express4J
--------------------------------------------------------------------------------------------------------------------------------------
[中文版本](https://github.com/aCoder2013/Express4J/blob/master/README-ZH.MD)
<br>
A Simple Java Web Framework inspired by [Express](http://expressjs.com/en/index.html),it's very easy to use and very suitable for
rapid development
#Quick Start :
```java
import static org.express4j.core.Express4J.*;

public class HelloWorld {

    public static void main(String[] args) {
        get("/hello",(req, res) ->res.renderHtml("Hello World"));
    }
}
```
##Basic Concept
###Route
<br>
-   Named Parameter
```java
get("/news/:id/",(request, response) -> {
    response.renderText(request.pathParam("id")+":"+request.pathParam("detailId"));
});
```

-   Wildcard 
```java
get("/hello/*/to/*",(request, response) ->
    response.renderHtml("Hello "+request.pathParam("0")+" To "+request.pathParam("1")));
```

-   Modularity:
```java
import org.junit.BeforeClass;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.express4j.core.Express4J.controller;
import static org.express4j.core.Express4J.run;

public class UserControllerTest {
    public static void main(String args[]){
        controller("/user").with(UserController.class);
        setBaseUrl("http://localhost:8080");
    }
}

public class UserController {



    @RequestMapping(value = "/login",method = HttpMethod.GET)
    public Handler login() {
        return (request, response) -> {
            response.renderHtml("Hello UserController");
        };
    }
    //The default is the GET method
    @RequestMapping("/register")
    public Handler register(){
        return (request, response) -> response.renderHtml("<h2>Register</h2>");
    }
}
```
-   Routes file
    *   This file lists all of the routes needed by the application. Each route consists of an HTTP method and URI pattern, both associated with a call to a handler.
    *    Let’s see what a route definition looks like:
```
GET /hello  controller.HelloRoute.hello(id:java.lang.Boolean,name:java.lang.String)
GET /world  controller.HelloRoute.world
```java
    Each route starts with the HTTP method, followed by the URI pattern. The last element is the call definition.
##Interceptor
```java
public class LoginInterceptor implements Interceptor{

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

Express4J.get("/list/detail/*",(request1, response1) ->
                System.out.println("list/detail/*")
        );
Express4J.addInterceptor("/list/*", LoginInterceptor.class);
```
###Custom Exception Handler
```java
get("/error", (request, response) -> {
    throw new UserNotFoundException();
});
@ExceptionInterceptor
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatusCode.ACCEPTED)
    @ExceptionHandler(UserNotFoundException.class)
    public User handlerUserNotFoundException(HttpServletRequest request,UserNotFoundException e){
        return new User(request.getRequestURI(),23,e.toString());
    }
}
```

