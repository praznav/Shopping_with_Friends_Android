package shopping.Controller;

import shopping.Model.MainScreenModel;
import shopping.View.MainScreenView;

public class MainScreenController {
    private final MainScreenView view;
    private final MainScreenModel model;

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
