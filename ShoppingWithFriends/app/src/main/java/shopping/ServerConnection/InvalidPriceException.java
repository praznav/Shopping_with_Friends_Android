package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
public class InvalidPriceException extends Exception {
    public InvalidPriceException() {
        super("You have already registered interest in that product");
    }

    public InvalidPriceException(String message) {
        super(message);
    }
}
