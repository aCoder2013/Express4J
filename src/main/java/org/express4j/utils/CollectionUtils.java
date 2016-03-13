package org.express4j.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Song on 2016/3/13.
 */
public class CollectionUtils {


    /**
     * 判断 Collection 是否为空或者null
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }


    /**
     * 判断Map是否为空或者null
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

}
