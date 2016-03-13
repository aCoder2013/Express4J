# Express4J
以[Express](http://expressjs.com/en/index.html)为灵感的的轻量级JavaWeb框架,优雅且高效,完全不存在大量的XML文件以及注解
#快速开始 :
```java
import static org.express4j.core.Express4J.*;

public class HelloWorld {

    public static void main(String[] args) {
        get("/hello",(req, res) ->res.renderHtml("Hello World"));
    }
}
```
##基本概念
###路由
####Ant-Style
<br>
1.命名参数
```java
get("/news/{id}/",(request, response) -> {
    response.renderText(request.pathParam("id")+":"+request.pathParam("detailId"));
});
```

2.支持通配符
```
/hello/*/to/*
/hello/**/to/
/he?llo
```

3.同时路由支持模块化:
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
    //默认为GET方法
    @RequestMapping("/register")
    public Handler register(){
        return (request, response) -> response.renderHtml("<h2>Register</h2>");
    }
}
```
-   路由文件
    *   这个文件列出了应用中所有的路由信息.每个路由包括HTTP方法、URI模板和与其关联的处理器.
    *   让我们看看路由文件长什么样子：
```java
GET /hello  controller.HelloRoute.hello(id:java.lang.Boolean,name:java.lang.String)
GET /world  controller.HelloRoute.world
```
    每个路由都由HTTP方法开头，紧接着是URI模板，最后是处理器的定义。
##Interceptor
###拦截器
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
###自定义异常处理
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


