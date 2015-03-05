package shopping.ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class RegisteredInterestAlreadyExistsException extends Exception {
    public RegisteredInterestAlreadyExistsException() {
        super("The specified user is invalid");
    }

    public RegisteredInterestAlreadyExistsException(String message) {
        super(message);
    }
}
