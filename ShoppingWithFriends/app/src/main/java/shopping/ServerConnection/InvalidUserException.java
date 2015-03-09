package shopping.ServerConnection;

/**
 * Exception for when the user tries to perform an action and specifies an invalid user to perform
 * it on or for.
 *
 * @author Zachary Peterson
 */
public class InvalidUserException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public InvalidUserException() {
        super("The specified user is invalid");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public InvalidUserException(String message) {
        super(message);
    }
}
