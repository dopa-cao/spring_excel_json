package cn.com.clm.exception;

/**
 * describe: custom exception
 *
 * @author liming.cao
 */
public class CustomException extends RuntimeException{

    private final transient Object[] parameters;

    public CustomException(String message) {
        this(message, (Object[])null);
    }

    public CustomException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
