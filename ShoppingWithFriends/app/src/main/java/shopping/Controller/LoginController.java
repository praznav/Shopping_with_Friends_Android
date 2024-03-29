package shopping.Controller;

import android.app.Activity;
import android.content.Intent;

import shopping.Activities.MainScreenActivity;
import shopping.Model.LoginModel;
import shopping.View.LoginView;



/**
 * Created by Keil on 2/25/15.
 */
public class LoginController {
    private LoginView view;
    private LoginModel model;

    public LoginController(LoginView v) {
        view = v;
        model = new LoginModel(this);
    }

    /**
     * Handler for login being clicked
     * Passes username and password to model
     * Authenticates and returns whether or not it was successful
     */
    public void onLoginClick() {
        String username = view.getUsername();
        String password = view.getPassword();
        model.setUsername(username);
        model.setPassword(password);
        String message = model.attemptLogin();
        if (message.equals("Success"))
            onCorrectCredentials(username, password);
        else {
            view.displayError(message);
        }

    }

    /**
     * Called when credentials are legitimate.  Sets the shared preferences and starts the next activity
     * @param username The correct username
     * @param password The correct password
     */
    public void onCorrectCredentials(String username, String password)
    {
        // Go to main app
        Intent intent = new Intent((Activity) view, MainScreenActivity.class);

        view.setSharedPreferences(username, password);

        view.startNewActivity(intent);

    }
}
