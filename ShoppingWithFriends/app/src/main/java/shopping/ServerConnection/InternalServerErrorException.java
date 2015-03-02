package shopping.ServerConnection;

/**
 * Created by Zach on 2/20/2015.
 */
public class InternalServerErrorException extends Exception {
    public InternalServerErrorException() {
        super("Unable to connect to server");
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
