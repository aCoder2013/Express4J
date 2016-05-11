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
####Ant-Style
<br>
-   Named Parameter
```java
get("/news/{id}/",(request, response) -> {
    response.renderText(request.pathParam("id")+":"+request.pathParam("detailId"));
});
```

-   Wildcard 
```
/hello/*/to/*
/hello/**/to/
/he?llo
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
```java
GET /hello  controller.HelloRoute.hello(id:java.lang.Boolean,name:java.lang.String)
GET /world  controller.HelloRoute.world
```
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
##A Simple CRUD Demo
```java
public class BooksControllerTest {

    private static Map<String, Book> books = new HashMap<String, Book>();

    public static void main(String[] args) {
        final Random random = new Random();

        get("/books", (request, response) -> {
            String author = request.param("author");
            String title = request.param("title");
            Book book = new Book(author, title);
            int id = random.nextInt(Integer.MAX_VALUE);
            books.put(String.valueOf(id), book);
            response.status(201).json(id);
        });


        get("/book/{id}",(request, response) -> {
           Book book = books.get(request.pathParam("id"));
            if(book != null){
                response.json(book);
            }else {
                response.status(404).renderHtml("<h1>404</h1>");
            }
        });

        put("/book/{id}",(request, response) -> {
            String id = request.pathParam("id");
            Book book = books.get(id);
            if(book != null){
                String title = request.param("title");
                String author = request.param("author");
                if(title != null && !title.isEmpty()){
                    book.setTitle(title);
                }
                if(author != null && !author.isEmpty()){
                    book.setAuthor(author);
                }
                response.renderHtml("Book with id " + id+" updated");
            }else {
                response.status(404).renderText("404");
            }

        });


        delete("/book/{id}",(request, response) -> {
            String id = request.pathParam("id");
            Book book = books.remove(id);
            if(book != null){
                response.renderText("Book with id "+ id +" deleted !");
            }else {
                response.status(404).renderText("404");
            }
        });
    }

    public static class Book {

        private String author;
        private String title;

        public Book(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

```
