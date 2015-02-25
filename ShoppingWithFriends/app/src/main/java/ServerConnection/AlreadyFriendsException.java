package ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class AlreadyFriendsException extends Exception {
    public AlreadyFriendsException() {
        super("Unable to connect to server");
    }

    public AlreadyFriendsException(String message) {
        super(message);
    }
}
