package shopping.View;

import android.content.Intent;
import android.view.View;

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
     * Gets the email the user entered
     * @return The email the user entered
     */
    public String getEmail();

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
