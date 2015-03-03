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

    public RegisterInterestModel(RegisterInterestController c)
    {
        cont = c;
        message = "";

    }

    public void setUsername(String u)
    {
        username = u;
    }

    public void setPassword(String p)
    {
        password = p;
    }

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

    public class RegisterInterestTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {


            HttpClient httpclient;
            HttpPost httppost;
            ArrayList<NameValuePair> postParameters;
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://teamkevin.me/Sales/RegisterInterest");

            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", username));
            postParameters.add(new BasicNameValuePair("password", password));
            postParameters.add(new BasicNameValuePair("productName", item));
            postParameters.add(new BasicNameValuePair("maxPrice","" + maxPrice));

            try {
                httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                HttpResponse response = httpclient.execute(httppost);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = rd.readLine()) != null)
                    sb.append(line);
                line = sb.toString();
                rd.close();

                Log.d("https", "msg" + line);
                if (line.contains("success"))
                {
                    message = "Successfully registered interest";
                    return true;
                }
            } catch (IOException e) {
                Log.d("HTTP post error", "" + e.getMessage());
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
