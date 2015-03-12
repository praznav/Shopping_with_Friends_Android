package shopping.View;

import android.content.Intent;
import android.view.View;

/**
 * Created by Keshanz on 3/2/2015.
 */
public interface PostSaleView {
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
     * Gets the item that the user wants to register sale for
     * @return The item name that the user wants to post sale for
     */
    public String getItem();

    /**
     * Gets the location from the text field
     * @return The location of the item
     */
    public String getLocation();

    /**
     * Gets the price that the user found
     * @return The price the user found
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
     * Clears the interest fields so that the user can input more interests
     */
    public void clearFields();
}
