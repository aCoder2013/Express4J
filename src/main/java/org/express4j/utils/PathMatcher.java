package org.express4j.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * 基于String的路径匹配策略类
 * 一部分代码来自Spring
 * 默认实现类为{@link org.express4j.utils.AntPathMatcher},支持Ant-style模式语法
 * Created by Song on 2016/3/13.
 */
public interface PathMatcher {

    /**
     * 判断给定{@code path} 是否为一种由接口实现类定义的模式
     * @param path 要检查的路径
     * @return {@code true} 如果代表模式
     */
    boolean isPattern(String path);

    /**
     * 判断 {@code path} 是否和 {@code pattern} 匹配
     * @param pattern 被用来匹配的模式
     * @param path 用来测试的路径
     * @return {@code true} 如果{@code path} 匹配,
     */
    boolean match(String pattern, String path);

    /**
     * Match the given {@code path} against the corresponding part of the given
     * {@code pattern}, according to this PathMatcher's matching strategy.
     * <p>Determines whether the pattern at least matches as far as the given base
     * path goes, assuming that a full path may then match as well.
     * @param pattern the pattern to match against
     * @param path the path String to test
     * @return {@code true} if the supplied {@code path} matched,
     * {@code false} if it didn't
     */
    boolean matchStart(String pattern, String path);

    /**
     * Given a pattern and a full path, determine the pattern-mapped part.
     * <p>This method is supposed to find out which part of the path is matched
     * dynamically through an actual pattern, that is, it strips off a statically
     * defined leading path from the given full path, returning only the actually
     * pattern-matched part of the path.
     * <p>For example: For "myroot/*.html" as pattern and "myroot/myfile.html"
     * as full path, this method should return "myfile.html". The detailed
     * determination rules are specified to this PathMatcher's matching strategy.
     * <p>A simple implementation may return the given full path as-is in case
     * of an actual pattern, and the empty String in case of the pattern not
     * containing any dynamic parts (i.e. the {@code pattern} parameter being
     * a static path that wouldn't qualify as an actual {@link #isPattern pattern}).
     * A sophisticated implementation will differentiate between the static parts
     * and the dynamic parts of the given path pattern.
     * @param pattern the path pattern
     * @param path the full path to introspect
     * @return the pattern-mapped part of the given {@code path}
     * (never {@code null})
     */
    String extractPathWithinPattern(String pattern, String path);

    /**
     * Given a pattern and a full path, extract the URI template variables. URI template
     * variables are expressed through curly brackets ('{' and '}').
     * <p>For example: For pattern "/hotels/{hotel}" and path "/hotels/1", this method will
     * return a map containing "hotel"->"1".
     * @param pattern the path pattern, possibly containing URI templates
     * @param path the full path to extract template variables from
     * @return a map, containing variable names as keys; variables values as values
     */
    Map<String, String> extractUriTemplateVariables(String pattern, String path);

    /**
     * Given a full path, returns a {@link Comparator} suitable for sorting patterns
     * in order of explicitness for that path.
     * <p>The full algorithm used depends on the underlying implementation, but generally,
     * the returned {@code Comparator} will
     * {@linkplain java.util.Collections#sort(java.util.List, Comparator) sort}
     * a list so that more specific patterns come before generic patterns.
     * @param path the full path to use for comparison
     * @return a comparator capable of sorting patterns in order of explicitness
     */
    Comparator<String> getPatternComparator(String path);

    /**
     * Combines two patterns into a new pattern that is returned.
     * <p>The full algorithm used for combining the two pattern depends on the underlying implementation.
     * @param pattern1 the first pattern
     * @param pattern2 the second pattern
     * @return the combination of the two patterns
     * @throws IllegalArgumentException when the two patterns cannot be combined
     */
    String combine(String pattern1, String pattern2);

}
