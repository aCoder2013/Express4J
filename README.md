# Express4J
一个[Express](http://expressjs.com/en/index.html)风格的轻量级JavaWeb框架,优雅且高效,完全不存在大量的XML文件以及注解充斥你的项目
#快速开始 :
```java
import static org.express4j.core.Express4J.*;

public class HelloWorld {

    public static void main(String[] args) {
        get("/hello",(req, res) ->res.renderHtml("Hello World"));
        run();
    }
}
```
##基本概念
1.  路由
<br>
支持通配符以及命名参数
```java
    get("/news/:id/",(request, response) -> {
        response.renderText(request.pathParam("id")+":"+request.pathParam("detailId"));
    });

    get("/hello/*/to/*",(request, response) ->
        response.renderHtml("Hello "+request.pathParam("0")+" To "+request.pathParam("1")));
```
2.  拦截器
```java

Express4J.get("/list/detail/*",(request1, response1) ->
                System.out.println("list/detail/*")
        );

Express4J.addInterceptor("/list/*", LoginInterceptor.class);
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
```
