package shopping.ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class InvalidUserException extends Exception {
    public InvalidUserException() {
        super("The specified user is invalid");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
