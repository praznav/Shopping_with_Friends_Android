package shopping.Model;

/**
 * Created by Keshanz on 3/11/2015.
 */
public class Sale {
    String postingUser;
    String productName;
    double priceFound;
    double priceRequested;
    String location;
    public Sale(String user, String product, double found, double req, String loc)
    {
        postingUser = user;
        productName = product;
        priceFound = found;
        priceRequested = req;
        location = loc;
    }

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
