package org.express4j.utils;

/**
 * Created by Song on 2015/12/26.
 */
public class ConvertUtils {

    public static Object convert(Class<?> cls ,Object value){
        if(Integer.class.isAssignableFrom(cls)){
            return Integer.parseInt((String) value);
        }

        if(Long.class.isAssignableFrom(cls)){
            return Long.parseLong((String) value);
        }

        if(Byte.class.isAssignableFrom(cls)){
            return Byte.parseByte((String) value);
        }

        if(Short.class.isAssignableFrom(cls)){
            return Short.parseShort((String) value);
        }

        if(Float.class.isAssignableFrom(cls)){
            return Float.parseFloat((String) value);
        }

        if(Double.class.isAssignableFrom(cls)){
            return Double.parseDouble((String) value);
        }

        if(Boolean.class.isAssignableFrom(cls)){
            return Boolean.parseBoolean((String) value);
        }
        return cls.cast(value);
    }

}
