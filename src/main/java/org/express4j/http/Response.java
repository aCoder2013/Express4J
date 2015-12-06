package org.express4j.http;

import org.express4j.utils.JsonUtils;

import javax.servlet.http.Cookie;
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

    public Response status(int code){
        servletResponse.setStatus(code);
        return this;
    }

    public void renderText(String text){
        getWriter().write(text);
        getWriter().flush();
        getWriter().close();
    }

    public void renderHtml(String htmlContent){
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

    public void json(Object jsonContent){
        servletResponse.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
        servletResponse.setHeader("Cache-Control", "no-cache");
        servletResponse.setDateHeader("Expires", 0);
        servletResponse.setContentType("application/json;charset=UTF-8");
        String json = JsonUtils.toJson(jsonContent);
        getWriter().write(json);
        getWriter().flush();
        getWriter().close();
    }


    /**
     * Sends a JSON response with JSONP support.
     * This method is identical to res.json(),
     * except that it opts-in to JSONP callback support.
     * @param callback name of the callback method name
     * @param jsonContent content in json format
     */
    public void jsonp(String callback,Object jsonContent){
        servletResponse.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
        servletResponse.setHeader("Cache-Control", "no-cache");
        servletResponse.setDateHeader("Expires", 0);
        servletResponse.setContentType("application/json;charset=UTF-8");
        String json = JsonUtils.toJson(jsonContent);
        json = callback+"("+json+");";
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

    /**
     * Adds not persistent cookie to the response.
     * Can be invoked multiple times to insert more than one cookie.
     *
     * @param name  name of the cookie
     * @param value value of the cookie
     */
    public void cookie(String name, String value) {
        cookie(name, value, -1, false);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param name   name of the cookie
     * @param value  value of the cookie
     * @param maxAge max age of the cookie in seconds (negative for the not persistent cookie,
     *               zero - deletes the cookie)
     */
    public void cookie(String name, String value, int maxAge) {
        cookie(name, value, maxAge, false);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param name    name of the cookie
     * @param value   value of the cookie
     * @param maxAge  max age of the cookie in seconds (negative for the not persistent cookie, zero - deletes the cookie)
     * @param secured if true : cookie will be secured
     */
    public void cookie(String name, String value, int maxAge, boolean secured) {
        cookie(name, value, maxAge, secured, false);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param name     name of the cookie
     * @param value    value of the cookie
     * @param maxAge   max age of the cookie in seconds (negative for the not persistent cookie, zero - deletes the cookie)
     * @param secured  if true : cookie will be secured
     * @param httpOnly if true: cookie will be marked as http only
     */
    public void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly) {
        cookie("", name, value, maxAge, secured, httpOnly);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param path    path of the cookie
     * @param name    name of the cookie
     * @param value   value of the cookie
     * @param maxAge  max age of the cookie in seconds (negative for the not persistent cookie, zero - deletes the cookie)
     * @param secured if true : cookie will be secured
     */
    public void cookie(String path, String name, String value, int maxAge, boolean secured) {
        cookie(path, name, value, maxAge, secured, false);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param path     path of the cookie
     * @param name     name of the cookie
     * @param value    value of the cookie
     * @param maxAge   max age of the cookie in seconds (negative for the not persistent cookie, zero - deletes the cookie)
     * @param secured  if true : cookie will be secured
     * @param httpOnly if true: cookie will be marked as http only
     */
    public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(secured);
        cookie.setHttpOnly(httpOnly);
        servletResponse.addCookie(cookie);
    }

    /**
     * Add cookie to the response
     * @param cookie
     */
    public void cookie(Cookie cookie){
        servletResponse.addCookie(cookie);
    }

    /**
     * Removes the cookie.
     *
     * @param name name of the cookie
     */
    public void removeCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
    }

}
