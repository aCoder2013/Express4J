package other;

import org.express4j.utils.AntPathMatcher;
import org.express4j.utils.PathMatcher;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Song on 2016/3/13.
 */
public class AntPathMatcherTest {

    private PathMatcher pathMatcher ;
    @Before
    public void setUp(){
        pathMatcher = new AntPathMatcher();
    }


    @Test
    public void test(){
        System.out.println(pathMatcher.match("/news/*","/news/"));
        System.out.println(pathMatcher.match("/news/*","/news"));
        System.out.println(pathMatcher.match("/news/*","/news/hello"));
        System.out.println(pathMatcher.match("/news/hello","/news/hello"));
        System.out.println(pathMatcher.match("/news/hell?","/news/hello"));
        System.out.println(pathMatcher.match("/news/{id}","/news/12312"));
        System.out.println(pathMatcher.extractUriTemplateVariables("/news/{id}/detail/{detailId}","/news/1234/detail/4564"));
        System.out.println(pathMatcher.extractPathWithinPattern("/news/*/detail/*","/news/1234/detail/4787465"));
        System.out.println(pathMatcher.combine("/news/","/123"));
        System.out.println(pathMatcher.matchStart("/news/123/detail","/news/"));
        System.out.println(pathMatcher.matchStart("/news/123/detail","/news/123"));
        System.out.println(pathMatcher.matchStart("/news/123/detail","/news/123/detail"));
    }
}
