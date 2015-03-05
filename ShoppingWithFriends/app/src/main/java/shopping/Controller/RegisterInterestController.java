package shopping.Controller;

import android.app.Activity;
import android.content.Intent;

import shopping.Activities.MainScreenActivity;
import shopping.Model.RegisterInterestModel;
import shopping.View.RegisterInterestView;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class RegisterInterestController {

    RegisterInterestView view;
    RegisterInterestModel model;

    public RegisterInterestController(RegisterInterestView v)
    {
        view = v;
        model = new RegisterInterestModel(this);
    }

    /**
     * Actual handler for confirm being clicked.
     * Passes in the username and password from view to the model
     * Attempts to post the interest
     * Displays any errors on the view
     */
    public void onConfirmClick()
    {
        model.setUsername(view.getUsername());
        model.setPassword(view.getPassword());
        view.displayError(model.postInterest(view.getItem(), view.getPrice()));
    }

    /**
     * Handler for return being clicked
     * Finishes the activity
     */
    public void onReturnClick()
    {
        view.finish();
    }
}
