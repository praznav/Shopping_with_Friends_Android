package ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class UnrecognizedResponseException extends Exception {
    public UnrecognizedResponseException() {
        super("The server response was unrecognized. Please try again later");
    }

    public UnrecognizedResponseException(String message) {
        super(message);
    }
}
