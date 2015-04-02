package shopping.Controller;

import shopping.Model.RegisterInterestModel;
import shopping.View.RegisterInterestView;

public class RegisterInterestController {
    private final RegisterInterestView view;
    private final RegisterInterestModel model;

    public RegisterInterestController(RegisterInterestView v)
    {
        view = v;
        model = new RegisterInterestModel();
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
        String message = model.postInterest(view.getItem(), view.getPrice());
        view.displayError(message);
        if (message.equals("Successfully registered interest"))  view.clearFields();
    }

}
