package shopping.ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Unable to connect to server");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
