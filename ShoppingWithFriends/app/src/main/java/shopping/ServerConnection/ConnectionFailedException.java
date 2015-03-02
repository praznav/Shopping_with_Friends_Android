package shopping.ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() {
        super("Unable to connect to server");
    }

    public ConnectionFailedException(String message) {
        super(message);
    }
}
