package shopping.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import shopping.Controller.RegisterInterestController;
import shopping.R;
import shopping.ServerConnection.AgaviServerConnection;
import shopping.ServerConnection.ServerConnection;

/**
 * Created by Keshanz on 3/2/2015.
 */
public class RegisterInterestModel {
    private RegisterInterestController cont;
    private String username;
    private String password;
    private String message;
    private double maxPrice;
    private String item;
    private RegisterInterestTask mInterestTask;
    private ServerConnection serv;

    public RegisterInterestModel(RegisterInterestController c)
    {
        cont = c;
        message = "";
        serv = new AgaviServerConnection();

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
     * Method called by the controller to post an interest
     * @param i The product name to post
     * @param p The max price to post
     * @return Message to display to the user
     */
    public String postInterest(String i, double p)
    {
        message = "Failed to post";
        item = i;
        maxPrice = p;
        mInterestTask = new RegisterInterestTask();
        try {
            mInterestTask.execute((Void) null).get();
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
    private class RegisterInterestTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            try
            {
                boolean b = serv.AddInterest(new User("" ,"" ,username ,password ,""), item, maxPrice);
                message = "Successfully registered interest";
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
            mInterestTask = null;
        }

        @Override
        protected void onCancelled() {
            mInterestTask = null;
        }
    }
}
