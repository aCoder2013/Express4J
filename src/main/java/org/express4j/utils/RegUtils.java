package org.express4j.utils;

import java.util.regex.Pattern;

/**
 * Created by Song on 2015/12/12.
 */
public class RegUtils {


    public static boolean matches(String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }
}
