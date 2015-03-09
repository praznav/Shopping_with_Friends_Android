package shopping.ServerConnection;

/**
 * Exception for when the user tries to perform an action with invalid login credentials.
 *
 * @author Zachary Peterson
 */
public class InvalidCredentialsException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public InvalidCredentialsException() {
        super("Unable to connect to server");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
