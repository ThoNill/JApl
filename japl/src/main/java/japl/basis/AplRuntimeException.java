package japl.basis;

public class AplRuntimeException extends RuntimeException{

    public AplRuntimeException() {
        super();
    }

    public AplRuntimeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AplRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AplRuntimeException(String message) {
        super(message);
    }

    public AplRuntimeException(Throwable cause) {
        super(cause);
    }

}
