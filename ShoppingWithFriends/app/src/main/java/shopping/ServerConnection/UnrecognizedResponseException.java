package shopping.ServerConnection;

/**
 * Exception for when the ServerConnection receives an unrecognized response from whatever server
 * type it is connecting to.
 *
 * @author Zachary Peterson
 */
public class UnrecognizedResponseException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public UnrecognizedResponseException() {
        super("The server response was unrecognized. Please try again later");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public UnrecognizedResponseException(String message) {
        super(message);
    }
}
