package org.express4j.utils;

import org.express4j.http.RequestFactory;

public class PathMatchUtils {
    public static boolean matches(String template, String path) {
        String[] templatePathList = template.split("/");
        String[] pathList = path.split("/");
        if (templatePathList.length == pathList.length) {
            int length = templatePathList.length;
            int i = 0;
            while (i < length) {
                String partPath = pathList[i];
                String partTemplatePath = templatePathList[i];
                if (!partPath.equals(partTemplatePath) && !partTemplatePath.startsWith(":") && !partTemplatePath.equals("*")) {
                    return false;
                }
                i++;
            }
            int index = 0;
            for (i = 0; i < templatePathList.length; i++) {
                if (templatePathList[i].startsWith(":")) {
                    String name = templatePathList[i].substring(templatePathList[i].indexOf(":") + 1);
                    String value = pathList[i];
                    RequestFactory.getRequest().addPathVariable(name, value);
                } else if (templatePathList[i].equals("*")) {
                    RequestFactory.getRequest().addPathVariable("" + index++, pathList[i]);
                }
            }
            return true;
        } else {
            if (templatePathList.length < pathList.length) {
                int i = 0;
                while (i < templatePathList.length) {
                    if (!templatePathList[i].equals(pathList[i]) && !templatePathList[i].equals("*")) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        return false;
    }
}