package shopping.ServerConnection;

import java.util.HashMap;

/**
 * Exception for when the user tries to submit a form with invalid data.
 *
 * @author Zachary Peterson
 */
public class InvalidDataException extends Exception {
    private HashMap<String, String> mInvalidFields;

    /**
     * Constructs a new exception with default message.
     */
    public InvalidDataException() {
        super("The given form contains invalid data.");
        mInvalidFields = new HashMap<>();
    }

    /**
     * Constructs a new exception with a given message.
     * @param message The message to use for the exception.
     */
    public InvalidDataException(String message) {
        super(message);
        mInvalidFields = new HashMap<>();
    }

    /**
     * Adds an invalid field and its error message to this exception
     * @param fieldName The name of the field that is invalid.
     * @param error The reason it is invalid.
     */
    public void AddInvalidField(String fieldName, String error) {
        mInvalidFields.put(fieldName, error);
    }

    /**
     * Gets a list of all the invalid fields and their corresponding errors.
     * @return A HashMap with all of the invalid fields and their corresponding errors.
     */
    public HashMap<String, String> GetInvalidFields() {
        return ((HashMap<String, String>) mInvalidFields.clone());
    }
}
