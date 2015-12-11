package other;

import org.express4j.core.InterceptorScanner;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Song on 2015/12/11.
 */
public class ICTest {

    @Test
    public void test(){
        InterceptorScanner scanner = new InterceptorScanner();
        scanner.init();
        Set<Class<?>> classHashSet = scanner.getIntercetporSet();
        for(Class<?> c :classHashSet){
            System.out.println(c.getName());
        }


    }
}
