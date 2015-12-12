package other;

import controller.UserNotFoundException;
import org.junit.Test;

/**
 * Created by Song on 2015/12/11.
 */
public class ExceptionTest {

    @Test
    public void test(){
        System.out.println(Exception.class.isAssignableFrom(UserNotFoundException.class));
    }
}
