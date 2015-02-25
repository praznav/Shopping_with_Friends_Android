package ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class UsernameTakenException extends Exception {
    public UsernameTakenException() {
        super("The given username is already taken.");
    }

    public UsernameTakenException(String message) {
        super(message);
    }
}
