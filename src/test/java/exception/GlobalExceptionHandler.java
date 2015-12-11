package exception;

import controller.UserNotFoundException;
import model.User;
import org.express4j.annotation.ExceptionHandler;
import org.express4j.annotation.ExceptionInterceptor;
import org.express4j.annotation.ResponseStatus;
import org.express4j.http.HttpStatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Song on 2015/12/11.
 */
@ExceptionInterceptor
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatusCode.ACCEPTED)
    @ExceptionHandler(UserNotFoundException.class)
    public User handlerUserNotFoundException(HttpServletRequest request,UserNotFoundException e){
        return new User(request.getRequestURI(),23,e.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(){

    }

    @ExceptionHandler(IllegalAccessException.class)
    public void handleIllegalAccessError(){

    }

}
