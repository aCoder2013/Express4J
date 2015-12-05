package org.express4j.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Song on 2015/12/5.
 */
public class JsonUtils {
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();



    public static String toJson(Object object){
        return gson.toJson(object);
    }

}
