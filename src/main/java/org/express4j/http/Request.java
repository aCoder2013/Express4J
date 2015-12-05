package org.express4j.http;

import org.express4j.utils.IOUtils;
import org.express4j.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class Request {

    private final HttpServletRequest servletRequest;

    private Map<String,String> params = new HashMap<>();

    private boolean paramParsed = false;

    private byte[] bodyBytes;

    private boolean bodyParsed = false;

    private Map<String,String>  headers = new HashMap<>();
    private boolean headerParsed = false;

    public Request(HttpServletRequest request) {
        this.servletRequest = request;
    }

    /**
     * @return the protocol of the request eg: http/https
     */
    public String protocol(){
        return servletRequest.getScheme();
    }


    /**
     * @return the host
     */
    public String host() {
        return servletRequest.getHeader("host");
    }

    /**
     * @return the uri without queryString
     */
    public String baseUrl(){
        return servletRequest.getRequestURI();
    }

    /**
     * @return the context path
     */
    public String contextPath(){
        return servletRequest.getContextPath();
    }

    /**
     * @return the path part of the request URL.
     */
    public String pathInfo(){
        return servletRequest.getPathInfo();
    }

    /**
     * @return query string of request
     */
    public String queryString(){
        return servletRequest.getQueryString();
    }
    /**
     * @return the http method
     */
    public String method(){
        return servletRequest.getMethod();
    }

    /**
     * @return the content type of body
     */
    public String contentType(){
        return servletRequest.getContentType();
    }

    /**
     * @return the length of content
     */
    public int contentLength(){
        return servletRequest.getContentLength();
    }

    /**
     * @return the ip
     */
    public String ip(){
        return servletRequest.getRemoteAddr();
    }

    /**
     * @param name
     * @return the header value with given name
     */
    public String header(String name){
        return servletRequest.getHeader(name);
    }

    /**
     * @return the headers of request
     */
    public Map<String,String> headers(){
        if(!headerParsed){
            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                String value = servletRequest.getHeader(name);
                headers.put(name,value);
            }
        }
        return Collections.unmodifiableMap(headers);
    }
    /**
     * @param name
     * @return the param value with the given name
     */
    public String param(String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        return servletRequest.getParameter(name);
    }


    /**
     * @return the params collection
     */
    public Map<String,String> params(){
        if(!paramParsed){
            Enumeration<String> parameterNames = servletRequest.getParameterNames();
            while(parameterNames.hasMoreElements()){
                String name = parameterNames.nextElement();
                String value = servletRequest.getParameter(name);
                params.put(name,value);
            }
        }
        return Collections.unmodifiableMap(params);
    }

    /**
     * @return the body
     */
    public String body(){
        if (!bodyParsed) {
            parseBody();
        }
        return new String(bodyBytes);
    }

    /**
     * Parse The Body
     * @throws IOException
     */
    private void parseBody(){
        try {
            bodyBytes = IOUtils.toByteArray(servletRequest.getInputStream());
            bodyParsed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

