package ServerConnection;

import java.util.HashMap;

/**
 * Created by Zach on 2/20/2015.
 */
public class InvalidDataException extends Exception {
    private HashMap<String, String> mInvalidFields;

    public InvalidDataException() {
        super("The given form contains invalid data.");
        mInvalidFields = new HashMap<String, String>();
    }

    public InvalidDataException(String message) {
        super(message);
        mInvalidFields = new HashMap<String, String>();
    }

    public void AddInvalidField(String fieldName, String error) {
        mInvalidFields.put(fieldName, error);
    }

    public HashMap<String, String> GetInvalidFields() {
        return ((HashMap<String, String>) mInvalidFields.clone());
    }
}
