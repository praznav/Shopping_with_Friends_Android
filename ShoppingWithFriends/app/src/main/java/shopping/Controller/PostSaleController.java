package shopping.Controller;

import shopping.Model.PostSaleModel;
import shopping.View.PostSaleView;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class PostSaleController {

    PostSaleView view;
    PostSaleModel model;

    public PostSaleController(PostSaleView v)
    {
        view = v;
        model = new PostSaleModel(this);
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
        String message = model.postSale(view.getItem(), view.getLocation(), view.getPrice());
        view.displayError(message);
        if (message.equals("Successfully posted sale"))  view.clearFields();
    }

}
