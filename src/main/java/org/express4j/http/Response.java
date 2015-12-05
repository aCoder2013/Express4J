package org.express4j.http;

import java.io.PrintWriter;

/**
 * Created by Song on 2015/12/4.
 */
public class Response {

    private PrintWriter writer;

    public Response setWriter(PrintWriter writer) {
        this.writer = writer;
        return this;
    }

    public void sendHtml(String htmlContent){
        writer.write(htmlContent);
        writer.flush();
        writer.close();
    }

    public void redirect(String path){
        //todo
    }

    public void sendStaticFile(String path){
        //todo
    }


}
