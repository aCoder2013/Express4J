package other;

import org.express4j.core.ExceptionHandlerFactory;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Song on 2015/12/11.
 */
public class ICTest {

    @Test
    public void test(){
        ExceptionHandlerFactory scanner = new ExceptionHandlerFactory();
        scanner.init();
        Set<Class<?>> classHashSet = scanner.getInterceptors();
        for(Class<?> c :classHashSet){
            System.out.println(c.getName());
        }


    }
}
