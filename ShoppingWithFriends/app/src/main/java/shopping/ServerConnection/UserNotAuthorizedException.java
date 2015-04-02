package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException() {
        super("You are not logged in");
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
