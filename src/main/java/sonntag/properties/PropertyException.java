package sonntag.properties;

/**
 * A PropertyException is an unchecked exception that basically
 * is a container for all exceptions that might be thrown by
 * this library.
 */
public class PropertyException extends RuntimeException {

    public PropertyException(String message) {
        super(message);
    }

    public PropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
