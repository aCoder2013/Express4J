package org.express4j.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2015/12/26.
 */
public class ArrayUtils {


    public static <T> List<T>  toList(T[] array ){
        List<T> list = new ArrayList<>();
        for(T t : array){
            list.add(t);
        }
        return list;
    }
}
