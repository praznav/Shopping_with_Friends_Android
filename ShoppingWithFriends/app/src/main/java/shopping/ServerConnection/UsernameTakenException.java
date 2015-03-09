package shopping.ServerConnection;

/**
 * Exception for when the user tries to register a username that is already taken.
 *
 * @author Zachary Peterson
 */
public class UsernameTakenException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public UsernameTakenException() {
        super("The given username is already taken.");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public UsernameTakenException(String message) {
        super(message);
    }
}
