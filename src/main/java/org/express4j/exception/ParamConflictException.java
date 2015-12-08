package org.express4j.exception;

/**
 * Created by Song on 2015/12/8.
 */
public class ParamConflictException extends  RuntimeException {

    public ParamConflictException() {
        super();
    }

    public ParamConflictException(String message) {
        super(message);
    }

    public ParamConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamConflictException(Throwable cause) {
        super(cause);
    }

    protected ParamConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
