package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
class InvalidProductNameException extends Exception {
    public InvalidProductNameException() {
        super("The product name inputted is invalid");
    }

    public InvalidProductNameException(String message) {
        super(message);
    }
}
