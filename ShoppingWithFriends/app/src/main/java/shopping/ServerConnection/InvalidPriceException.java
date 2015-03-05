package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
public class InvalidPriceException extends Exception {
    public InvalidPriceException() {
        super("The price entered is invalid");
    }

    public InvalidPriceException(String message) {
        super(message);
    }
}
