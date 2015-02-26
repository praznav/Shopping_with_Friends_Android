package ServerConnection;

/**
 * Exception for when the ServerConnection cannot reach the server.
 *
 * @author Zachary Peterson
 */
public class ConnectionFailedException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public ConnectionFailedException() {
        super("Unable to connect to server");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public ConnectionFailedException(String message) {
        super(message);
    }
}
