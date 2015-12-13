package org.express4j.utils;

/**
 * Created by Song on 2015/12/4.
 */
public class StringUtils {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    /**
     * 检查字符串是否为空
     * @param cs  要检查的字符
     * @return {@code true} 如果字符串为空或Null
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    public static int compute(String str,char ch){
        int count = 0 ;
        for(char c : str.toCharArray()){
            if(c==ch){
                count++;
            }
        }
        return count;
    }
}
