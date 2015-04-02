package shopping.View;

import android.content.Intent;
import android.view.View;

/**
 * Created by Nevin on 2/25/15.
 */
public interface LoginView {
    /**
     * Called whenever the login button is clicked
     * @param v The view that was clicked
     */
    public void onLoginClick(View v);

    /**
     * Called whenever the register button is clicked
     * @param v The view that was clicked
     */
    public void onRegisterClick(View v);

    /**
     * Gets the username the user entered
     * @return The username the user entered
     */
    public String getUsername();

    /**
     * Gets the password the user entered
     * @return The password the user entered
     */
    public String getPassword();

    /**
     * Displays an error message for the user
     * @param error The error message to display
     */
    public void displayError(String error);

    /**
     * Starts a new intent
     * @param i The intent to start
     */
    public void startNewActivity(Intent i);

    /**
     * Stores the username and password in the shared preferences
     * @param username The username to store
     * @param password The password to store
     */
    public void setSharedPreferences(String username, String password);
}
