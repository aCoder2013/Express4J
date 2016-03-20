package org.express4j.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by song on 16-3-20.
 */
public class PathUtils {

    /**
     * 规范化路径
     * 截取掉ContextPath,例如/ProjectName的形式
     * 如果以/结尾，也截取掉
     * @param request
     * @return
     */
    public static String canonicalize(HttpServletRequest request){
        String path = "";
        String contextPath = request.getContextPath();
        if (contextPath != "/") {
            path = request.getRequestURI().substring(contextPath.length());
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}
