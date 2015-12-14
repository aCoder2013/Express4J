# Express4J
A [Express](http://expressjs.com/en/index.html)风格的JavaWeb框架.
优雅且高效
快速开始 :
```java
import static org.express4j.core.Express4J.*;

public class HelloWorld {

    public static void main(String[] args) {
        get("/hello",(req, res) ->res.renderHtml("Hello World"));
        run();
    }
}

   
```
