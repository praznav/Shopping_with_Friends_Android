package shopping.ServerConnection;

/**
 * Created by Kevin on 3/4/2015
 */
class RegisteredItemAlreadyExistsException extends Exception {
    public RegisteredItemAlreadyExistsException() {
        super("You have already registered this item");
    }

    public RegisteredItemAlreadyExistsException(String message) {
        super(message);
    }
}
