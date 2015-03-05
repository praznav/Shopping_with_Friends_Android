package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
public class RegisteredInterestAlreadyExistsException extends Exception {
    public RegisteredInterestAlreadyExistsException() {
        super("You have already registered interest in that product");
    }

    public RegisteredInterestAlreadyExistsException(String message) {
        super(message);
    }
}
