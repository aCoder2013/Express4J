package org.express4j.core;

import org.express4j.annotation.ExceptionHandler;
import org.express4j.annotation.ExceptionInterceptor;
import org.express4j.annotation.ResponseStatus;
import org.express4j.handler.ExceptionHandlerWrapper;
import org.express4j.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 拦截器扫描器 Created by Song on 2015/12/11.
 */
public class ExceptionHandlerFactory {

  private static Set<Class<?>> interceptorSet = new HashSet<>();
  private static Map<Class<?>, ExceptionHandlerWrapper> handlerWrapperMap = new HashMap<>();

  /**
   * 初始化方法
   */
  public static void init() {
    loadInterceptorClass();
    if (!interceptorSet.isEmpty()) {
      for (Class<?> cls : interceptorSet) {
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
          if (m.isAnnotationPresent(ExceptionHandler.class)) {
            ExceptionHandler handler = m.getAnnotation(ExceptionHandler.class);
            Class<?> exceptionClass = handler.value();
            if (m.isAnnotationPresent(ResponseStatus.class)) {
              ResponseStatus responseStatusClass = m.getAnnotation(ResponseStatus.class);
              int code = responseStatusClass.value();
              handlerWrapperMap.put(exceptionClass, new ExceptionHandlerWrapper(cls, m, code));
            }
          }
        }
      }
    }
  }

  /**
   * 加载标有ExceptionInterceptor注解的类
   */
  private static void loadInterceptorClass() {
    Set<Class<?>> classSet = ClassUtils.getClassSet();
    interceptorSet.addAll(classSet.stream()
        .filter(cls -> cls.isAnnotationPresent(ExceptionInterceptor.class))
        .collect(Collectors.toList()));
  }

  public static Set<Class<?>> getInterceptors() {
    return interceptorSet;
  }

  public static Map<Class<?>, ExceptionHandlerWrapper> getHandlerWrapperMap() {
    return handlerWrapperMap;
  }


  public static ExceptionHandlerWrapper getExceptionHandlerWrapper(Class cls) {
    ExceptionHandlerWrapper wrapper = null;
    if (cls != null) {
        wrapper = handlerWrapperMap.get(cls);
    }
    return wrapper;
  }


}
