package shopping.View;

import android.content.Intent;
import android.view.View;

/**
 * Created by Keshanz on 3/2/2015.
 */
public interface RegisterInterestView {
    /**
     * Called when the confirm button is clicked
     * @param v The view that was clicked
     */
    public void onConfirmClick(View v);

    /**
     * Called when the return button is clicked
     * @param v The view that was clicked
     */
    public void onReturnClick(View v);

    /**
     * Gets the item that the user wants to register interest for
     * @return The item name that the user wants to register interest for
     */
    public String getItem();

    /**
     * Gets the maximum price that the user wants the item for
     * @return The maximum price that the user wants the item for
     */
    public double getPrice();

    /**
     * Gets the username for the user when sending the request
     * @return The username of the logged in user
     */
    public String getUsername();

    /**
     * Gets the password for the user when sending the request
     * @return The password of the logged in user
     */
    public String getPassword();

    /**
     * Displays an error for the user to see
     * @param error The error message to display
     */
    public void displayError(String error);

    /**
     * Starts a new intent
     * @param i The intent to start
     */
    public void startNewActivity(Intent i);

    /**
     * Finishes the current activity
     */
    public void finish();

    /**
     * Clears the interest fields so that the user can input more interests
     */
    public void clearFields();
}
