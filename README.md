# Express4J
A [Express](http://expressjs.com/en/index.html) Style Java Web Framework .
Here is a demonstrate:
```java
 get("/hello", (request, response) ->
            response.renderHtml("<h1>Hello World</h1>")
        );

        get("/error",(request, response) ->
            response.status(HttpStatusCode.BAD_REQUEST).renderText("Bad Request !"));

        get("/testjson",(request, response) ->
            response.json(new User("Mars",22,"123456789"))
        );

        get("/cookietest",(request, response) -> {
            System.out.println(request.cookies());
            response.renderHtml("<h1>"+request.cookie("blog")+"</h1>");
        });

        get("/setcookie",(request, response) ->{
            response.cookie("dznews","interesting");
            response.status(HttpStatusCode.OK).renderHtml("Success!");
        });


        get("/jsonp",(request, response) ->
            response.jsonp("user",new User("Tommy",23,"1994-11-15"))
        );
        get("/index",(request, response) ->{
            Father father = new Father("张无忌");
            User user = new User("Mars",23,"13245674",father);
            response.set("name","世界");
            response.set("user",user);
            response.send("index.ftl");
        });
        templatePath("freemarker");
        serverPort(80);
        run();
```
