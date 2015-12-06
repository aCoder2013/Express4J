package org.express4j.http;

import org.express4j.render.FreemarkerRender;
import org.express4j.utils.JsonUtils;
import org.express4j.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class Response {

    private PrintWriter writer;

    private HttpServletResponse servletResponse;

    private Map<String,Object> models = new HashMap<>();

    public Response(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }


    /**
     * Add key/value to model
     * @param name
     * @param value
     */
    public void set(String name,Object value){
        models.put(name,value);
    }



    /**
     * Set content type of response
     * @param contentType
     */
    public void type(String contentType){
        servletResponse.setContentType(contentType);
    }


    /**
     * set the HTTP status for the response.
     * @param code HTTP status code
     * @return Response itself
     */
    public Response status(int code){
        servletResponse.setStatus(code);
        return this;
    }

    /**
     * Renders a String as text
     * @param text
     */
    public void renderText(String text){
        getWriter().write(text);
        getWriter().flush();
        getWriter().close();
    }

    /**
     * Renders a String as HTML
     * @param htmlContent
     */
    public void renderHtml(String htmlContent){
        PrintWriter writer = getWriter();
        writer.write(htmlContent);
        writer.flush();
        writer.close();
    }

    /**
     * Help to get writer of response
     * @return
     */
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

    /**
     * Renders a Object as Json
     * @param jsonContent
     */
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
        servletResponse.setContentType("application/javascript");
        String json = JsonUtils.toJson(jsonContent);
        json = callback+"("+json+");";
        getWriter().write(json);
        getWriter().flush();
        getWriter().close();
    }

    public void send(String path){
        if(path.startsWith("/")){
            path.substring(0,path.length());
        }
        FreemarkerRender.getInstance().render(path, models, getWriter());
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
        cookie("/", name, value, maxAge, secured, httpOnly);
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
     * @param path     path of the cookie ,defaults to "/"
     * @param name     name of the cookie
     * @param value    value of the cookie
     * @param maxAge   max age of the cookie in seconds (negative for the not persistent cookie, zero - deletes the cookie)
     * @param secured  if true : cookie will be secured
     * @param httpOnly if true: cookie will be marked as http only
     */
    public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        if(StringUtils.isEmpty(path)){
            path = "/";
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(secured);
        cookie.setHttpOnly(httpOnly);
        servletResponse.addCookie(cookie);
    }

    /**
     * Sets the response Location HTTP header to the specified path parameter.
     * A path value of “back” has a special meaning,
     * it refers to the URL specified in the Referer header of the request.
     * If the Referer header was not specified, it refers to “/”.
     * @param path value of location header
     * @return
     */
    public Response location(String path){
        String location = path;
        if(path.equals("back")){
            location = !StringUtils.isEmpty(servletResponse.getHeader("Referrer"))
                    ?servletResponse.getHeader("Referrer"):"/";
        }
        servletResponse.addHeader("Location",location);
        return this;
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
