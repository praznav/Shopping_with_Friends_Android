package shopping.Controller;

import shopping.Model.MainScreenModel;
import shopping.View.MainScreenView;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class MainScreenController {

    MainScreenView view;
    MainScreenModel model;

    public MainScreenController(MainScreenView v)
    {
        view = v;
        model = new MainScreenModel(this);
    }

    /**
     * Updates the list of sales
     */
    public void refreshList()
    {
        model.setUsername(view.getUsername());
        model.setPassword(view.getPassword());
        view.displaySales(model.getSales());
    }

}
