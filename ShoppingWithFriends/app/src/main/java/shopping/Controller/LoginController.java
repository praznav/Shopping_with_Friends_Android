package shopping.Controller;

import android.app.Activity;
import android.content.Intent;

import shopping.Activities.MainScreenActivity;
import shopping.Model.LoginTask;
import shopping.View.LoginView;



/**
 * Created by Keil on 2/25/15.
 */
public class LoginController {
    private LoginView view;
    private LoginTask model;

    public LoginController(LoginView v) {
        view = v;
        model = new LoginTask(this);
    }

    public void onLoginClick() {
        String email = view.getEmail();
        String password = view.getPassword();
        model.setEmail(email);
        model.setPassword(password);
        String message = model.attemptLogin();
        if (message.equals("Success"))
            onCorrectCredentials(email, password);
        else {
            view.displayError(message);
        }

    }

    public void onCorrectCredentials(String username, String password)
    {
        // Go to main app
        Intent intent = new Intent((Activity) view, MainScreenActivity.class);

        view.setSharedPreferences(username, password);

        view.startNewActivity(intent);

    }
}
