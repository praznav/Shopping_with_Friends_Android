package shopping.View;

import java.util.List;

import shopping.Model.Sale;

public interface MainScreenView {

    /**
     * Displays the list of sales for the user's requests
     * @param sales  the list of sales to display
     */
    public void displaySales(List<Sale> sales);

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

}
