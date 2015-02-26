package ServerConnection;

/**
 * Exception for when the user is already friends with the user they are trying to friend.
 *
 * @author Zachary Peterson
 */
public class AlreadyFriendsException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public AlreadyFriendsException() {
        super("Unable to connect to server");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public AlreadyFriendsException(String message) {
        super(message);
    }
}
