package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException() {
        super("You have already registered interest in that product");
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
