package org.express4j.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Song on 2015/12/4.
 */
public class Response {

    private PrintWriter writer;

    private HttpServletResponse servletResponse;

    public Response(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }



    public void sendHtml(String htmlContent){
        PrintWriter writer = getWriter();
        writer.write(htmlContent);
        writer.flush();
        writer.close();
    }

    private PrintWriter getWriter() {
       if(writer==null){
           try {
               writer = servletResponse.getWriter();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        return writer;
    }

    public  void json(Object jsonContent){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(jsonContent);
        servletResponse.setContentType("application/json;charset=UTF-8");
        getWriter().write(json);
        getWriter().flush();
        getWriter().close();
    }

    public void redirect(String path){
        //todo
    }

    public void sendStaticFile(String path){
        //todo
    }


}
