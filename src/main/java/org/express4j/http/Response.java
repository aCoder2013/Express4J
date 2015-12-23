package org.express4j.http;

import org.apache.commons.io.IOUtils;
import org.express4j.core.Express4JConfig;
import org.express4j.render.FreemarkerRender;
import org.express4j.utils.ClassUtils;
import org.express4j.utils.JsonUtils;
import org.express4j.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpServletResponse包装类
 * Created by Song on 2015/12/4.
 */
public class Response {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

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
        servletResponse.setContentType(Http.CONTENT_TYPE_TEXT);
        getWriter().write(text);
        getWriter().flush();
        getWriter().close();
    }

    /**
     * Renders a String as HTML
     * @param htmlContent
     */
    public void renderHtml(String htmlContent){
        servletResponse.setContentType(Http.CONTENT_TYPE_HTML);
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
        servletResponse.setContentType(Http.CONTENT_TYPE_JSON);
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
        servletResponse.setContentType(Http.CONTENT_TYPE_JAVASCRIPT);
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
        FreemarkerRender.render(path, models, getWriter());
    }

    /**
     * Sends an error response to the client using the specified
     * status and clears the buffer.  The server defaults to creating the
     * response to look like an HTML-formatted server error page
     * containing the specified message, setting the content type
     * to "text/html". The server will preserve cookies and may clear or
     * update any headers needed to serve the error page as a valid response.
     *
     * If an error-page declaration has been made for the web application
     * corresponding to the status code passed in, it will be served back in
     * preference to the suggested msg parameter and the msg parameter will
     * be ignored.
     *
     * <p>If the response has already been committed, this method throws
     * an IllegalStateException.
     * After using this method, the response should be considered
     * to be committed and should not be written to.
     *
     * @param	sc	the error status code
     * @param	msg	the descriptive message
     * @exception	IOException	If an input or output exception occurs
     * @exception	IllegalStateException	If the response was committed
     */
    public void sendError(int sc, String msg) throws IOException{
        servletResponse.sendError(sc, msg);
    }

    /**
     * Sends an error response to the client using the specified status
     * code and clears the buffer.
     *
     * The server will preserve cookies and may clear or
     * update any headers needed to serve the error page as a valid response.
     *
     * If an error-page declaration has been made for the web application
     * corresponding to the status code passed in, it will be served back
     * the error page
     *
     * <p>If the response has already been committed, this method throws
     * an IllegalStateException.
     * After using this method, the response should be considered
     * to be committed and should not be written to.
     *
     * @param	sc	the error status code
     * @exception	IOException	If an input or output exception occurs
     * @exception	IllegalStateException	If the response was committed
     *						before this method call
     */
    public void sendError(int sc) throws IOException{
        servletResponse.sendError(sc);
    }

    /**
     * Sends a temporary redirect response to the client using the
     * specified redirect location URL and clears the buffer. The buffer will
     * be replaced with the data set by this method. Calling this method sets the
     * status code to {@link HttpStatusCode.302} 302 (Found).
     * This method can accept relative URLs;the servlet container must convert
     * the relative URL to an absolute URL
     * before sending the response to the client. If the location is relative
     * without a leading '/' the container interprets it as relative to
     * the current request URI. If the location is relative with a leading
     * '/' the container interprets it as relative to the servlet container root.
     * If the location is relative with two leading '/' the container interprets
     * it as a network-path reference (see
     * <a href="http://www.ietf.org/rfc/rfc3986.txt">
     * RFC 3986: Uniform Resource Identifier (URI): Generic Syntax</a>, section 4.2
     * &quot;Relative Reference&quot;).
     *
     * <p>If the response has already been committed, this method throws
     * an IllegalStateException.
     * After using this method, the response should be considered
     * to be committed and should not be written to.
     *
     * @param		location	the redirect location URL
     * @exception	IOException	If an input or output exception occurs
     * @exception	IllegalStateException	If the response was committed or
     *              if a partial URL is given and cannot be converted into a valid URL
     */
    public void redirect(String location){
        try {
            servletResponse.sendRedirect(location);
        } catch (IOException e) {
            logger.error("Redirect failure with location :" +location);
            e.printStackTrace();
        }
    }

    /**
     * 发送静态文件
     * @param path
     */
    public void sendStaticFile(String path){
        if(!path.startsWith("/")){
            path = "/"+path;
        }
        URL url = ClassUtils.getResourceUrl(Express4JConfig.getStaticFilePath()+path);
        if(url==null){
            logger.error("Resource not found :" +path);
            return;
        }
        try {
            IOUtils.copy(url.openStream(),getWriter());
            getWriter().flush();
        } catch (IOException e) {
            logger.error("Open InputStream Failure With given path :"+path);
            e.printStackTrace();
        }
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
