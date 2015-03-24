package shopping.Model;

import android.os.AsyncTask;

import shopping.Controller.PostSaleController;
import shopping.ServerConnection.AgaviServerConnection;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class PostSaleModel {
    private PostSaleController cont;
    private String username;
    private String password;
    private String message;
    private double foundPrice;
    private String location;
    private String item;
    private PostSaleTask mPostSaleTask;

    public PostSaleModel(PostSaleController c)
    {
        cont = c;
        message = "";

    }

    /**
     * Sets the username for the model
     * @param u The username to set
     */
    public void setUsername(String u)
    {
        username = u;
    }

    /**
     * Sets the password for the model
     * @param p The password to set
     */
    public void setPassword(String p)
    {
        password = p;
    }

    /**
     * Posts sale to the database
     * @param i item you're posting
     * @param l location where item was found
     * @param p price of the item found
     * @return
     */
    public String postSale(String i, String l, double p)
    {

        message = "Failed to post";
        location = l;
        item = i;
        foundPrice = p;
        mPostSaleTask = new PostSaleTask();
        try {
            mPostSaleTask.execute((Void) null).get();
        }
        catch(Exception e)
        {
            message = "Timed out";
        }
        return message;
    }

    /**
     * AsyncTask for registering interest.  Uses server connection class to post item and price into the database
     */
    private class PostSaleTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            try
            {
                boolean b = AgaviServerConnection.GetInstance().PostSale(new User("", "", username, password, ""), item, foundPrice, location);
                message = "Successfully posted sale";
                return b;
            }
            catch(Exception e)
            {
                message = e.getMessage();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mPostSaleTask = null;
        }

        @Override
        protected void onCancelled() {
            mPostSaleTask = null;
        }
    }
}
