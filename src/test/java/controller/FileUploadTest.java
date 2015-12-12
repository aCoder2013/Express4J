package controller;

import static org.express4j.core.Express4J.*;

/**
 * Created by Song on 2015/12/8.
 */
public class FileUploadTest {

    public static void main(String args[]){

        get("/index",(request1, response) ->{
            response.set("name","Mars");
            response.send("index.ftl");
        });
        get("/file",(request, response) -> response.send("file.ftl"));

        post("/process",(request, response) -> {
            System.out.println(request.getFile("img"));
        });
        serverPort(9000);
        run();
    }


}
