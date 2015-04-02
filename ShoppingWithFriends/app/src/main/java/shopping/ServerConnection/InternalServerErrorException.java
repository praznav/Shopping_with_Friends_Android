package shopping.ServerConnection;

/**
 * Exception for when an internal server error occurs.
 *
 * @author Zachary Peterson
 */
class InternalServerErrorException extends Exception {
    /**
     * Constructs a new exception with default message.
     */
    public InternalServerErrorException() {
        super("Unable to connect to server");
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public InternalServerErrorException(String message) {
        super(message);
    }
}
