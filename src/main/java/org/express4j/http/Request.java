package org.express4j.http;

import org.express4j.multipart.FileUploadHelper;
import org.express4j.multipart.MultipartFile;
import org.express4j.utils.IOUtils;
import org.express4j.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Song on 2015/12/4.
 */
public class Request {

    private final HttpServletRequest servletRequest;

    private RequestParam params = new RequestParam();

    private boolean paramParsed = false;

    private byte[] bodyBytes;

    private boolean bodyParsed = false;

    private Map<String,String>  headers = new HashMap<>();
    private boolean headerParsed = false;

    private Map<String,String> cookieMap = new HashMap<>();

    private boolean cookieParsed = false;

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
    public RequestParam params(){
        checkIfParsed();
        return params;
    }

    /**
     * 检查参数是否已经解析
     * 如果没有的话，则根据表单类型进行解析
     */
    private void checkIfParsed() {
        if(!paramParsed){
            //如果表单类型为multipart/form-data
            if (FileUploadHelper.isMultipart(servletRequest)) {
                FileUploadHelper.createMultipartForm(params, servletRequest);
            }else{
                Enumeration<String> parameterNames = servletRequest.getParameterNames();
                while(parameterNames.hasMoreElements()){
                    String name = parameterNames.nextElement();
                    String value = servletRequest.getParameter(name);
                    params.addRegularField(name, value);
                }
            }
        }
    }

    public MultipartFile getFile(String name){
        if(!FileUploadHelper.isMultipart(servletRequest)){
            return null;
        }
        if(!paramParsed){
            FileUploadHelper.createMultipartForm(params,servletRequest);
        }
        return params.getFile(name);
    }

    public List<MultipartFile> getFiles(String name){
        if(!FileUploadHelper.isMultipart(servletRequest)){
            return null;
        }
        if(!paramParsed){
            FileUploadHelper.createMultipartForm(params,servletRequest);
        }
        return params.getFiles(name);
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

    /**
     * @return the cookies of request
     */
    public Map<String,String> cookies(){
        if (!cookieParsed) {
            parseCookie();
        }
        return cookieMap;
    }

    /**
     * Parse cookies of the reuqest
     */
    private void parseCookie() {
        Cookie[] cookies = servletRequest.getCookies();
        for(Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            cookieMap.put(name, value);
        }
    }

    /**
     * @param name
     * @return Get value of cookie with given name
     */
    public String cookie(String name){
        if(!cookieParsed){
            parseCookie();
        }
        return cookieMap.get(name);
    }

}

