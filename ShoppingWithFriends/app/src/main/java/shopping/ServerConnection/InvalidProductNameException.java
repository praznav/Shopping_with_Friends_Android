package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
public class InvalidProductNameException extends Exception {
    public InvalidProductNameException() {
        super("You have already registered interest in that product");
    }

    public InvalidProductNameException(String message) {
        super(message);
    }
}
