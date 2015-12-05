package org.express4j.core;


import org.express4j.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2015/12/4.
 */
public class Express4JConfig {


    /**
     * 存储配置键值对
     * 键来自Express4JConstants
     */
    private static Map<String,String> CONFIG = new HashMap<>();

    /**
     * 设置属性
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        if (!StringUtils.isEmpty(key)) {
            CONFIG.put(key,value);
        }
    }

    /**
     * 得到Key对应的属性值
     * 若不存在咋返回默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String get(String key,String defaultValue){
        String value = defaultValue;
        if(CONFIG.containsKey(key)){
            value = CONFIG.get(key);
        }
        return value;
    }


    /**
     * 得到Key对应的布尔类型的属性值
     * 若不存在咋返回默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key,boolean defaultValue){
        boolean value = defaultValue;
        if(CONFIG.containsKey(key)){
            value  = Boolean.parseBoolean(CONFIG.get(key));
        }
        return value;
    }
    /**
     * 得到Key对应的Integer类型的属性值
     * 若不存在咋返回默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInteger(String key,int defaultValue){
        int value = defaultValue;
        if(CONFIG.containsKey(key)){
            value = Integer.parseInt(CONFIG.get(key));
        }
        return value;
    }
}
