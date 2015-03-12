package shopping.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import shopping.Controller.MainScreenController;
import shopping.ServerConnection.AgaviServerConnection;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class MainScreenModel {
    private MainScreenController cont;
    private String username;
    private String password;
    private double foundPrice;
    private String location;
    private String item;
    private List<Sale> saleList;
    private MainScreenTask mMainScreenTask;

    public MainScreenModel(MainScreenController c)
    {
        cont = c;
        saleList = new ArrayList<Sale>();

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
     * Gets the sales that match the current user's posted interests
     * @return A list of sales
     */
    public List<Sale> getSales()
    {


        mMainScreenTask = new MainScreenTask();
        try {
            mMainScreenTask.execute((Void) null).get();
        }
        catch(Exception e)
        {
        }
        return saleList;
    }

    /**
     * AsyncTask for the current activity.  Accesses database via internet
     */
    private class MainScreenTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            try
            {
                saleList = AgaviServerConnection.GetInstance().GetSales(new User("", "", username, password, ""));
                return true;
            }
            catch(Exception e)
            {
                Log.e("Exception", e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mMainScreenTask = null;
        }

        @Override
        protected void onCancelled() {
            mMainScreenTask = null;
        }
    }
}
