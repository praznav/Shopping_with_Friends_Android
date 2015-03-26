package shopping.Model;

/**
 * Created by Keshanz on 3/11/2015.
 */
public class Sale {
    // friend who made the sale post
    String postingUser;
    // name of product
    String productName;
    // price of product
    double priceFound;
    // max price willing to pay
    double priceRequested;
    // place of sale
    String location;
    public Sale(String user, String product, double found, double req, String loc)
    {
        postingUser = user;
        productName = product;
        priceFound = found;
        priceRequested = req;
        location = loc;
    }

    /*
        GETTERS START HERE
     */
    public String getUser()
    {
        return postingUser;
    }

    public String getProduct()
    {
        return productName;
    }

    public double getPriceFound()
    {
        return priceFound;
    }

    public double getPriceRequested()
    {
        return priceRequested;
    }

    public String getLocation()
    {
        return location;
    }
}
