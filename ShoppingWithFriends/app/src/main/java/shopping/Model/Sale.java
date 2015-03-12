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

    /**
     * Returns the user associated with this sale.
     * @return The user associated with this sale.
     */
    public String getUser()
    {
        return postingUser;
    }

    /**
     * Returns the name of the product associated with this sale.
     * @return The name of the product associated with this sale.
     */
    public String getProduct()
    {
        return productName;
    }

    /**
     * Returns the price of the product associated with this sale.
     * @return The price of the product associated with this sale.
     */
    public double getPriceFound()
    {
        return priceFound;
    }

    /**
     * Returns the maximum price the user set in the interest for this sale's product.
     * @return The maximum price the user set in the interest.
     */
    public double getPriceRequested()
    {
        return priceRequested;
    }

    /**
     * Returns the location of the product associated with this sale.
     * @return The location of the product associated with this sale.
     */
    public String getLocation()
    {
        return location;
    }
}
