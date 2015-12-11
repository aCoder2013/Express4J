package exception;

import controller.UserNotFoundException;
import org.express4j.annotation.ExceptionHandler;
import org.express4j.annotation.ExceptionInterceptor;

/**
 * Created by Song on 2015/12/11.
 */
@ExceptionInterceptor
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public void handlerUserNotFoundException(){
        System.out.println("哈哈handlerUserNotFoundException");
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(){

    }

    @ExceptionHandler(IllegalAccessException.class)
    public void handleIllegalAccessError(){

    }

}
