package org.express4j.core;

/**
 * {@link Ordered}可以被有顺序的对象实现，例如拦截器的执行顺序
 * Created by song on 16-3-20.
 */
public interface Ordered {

    /**
     * 常量：最高优先级
     */
    int HIGHEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 常量 : 最低优先级
     */
    int LOWEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 得到对象的优先级
     * 值越大，优先级越低
     * 相同优先级顺序随意
     * @return
     */
    int getOrder();
}
