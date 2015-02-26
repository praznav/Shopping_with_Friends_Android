package com.example.pranav.shoppingwithfriends;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


public class RegisterInterestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);
        final EditText txtEdit = (EditText) findViewById(R.id.price_edit_text);

        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double price = Double.parseDouble(txtEdit.getText().toString());
                    price = (Math.round(price * 100))/100.0;
                    txtEdit.setText("" + price, TextView.BufferType.EDITABLE);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_interest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RegisterInterestTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;
        private String itemOfInterest;
        private double price;

        /**
         * Constructor to create add friend task
         * @param email current user's email
         * @param password current user's password
         */
        RegisterInterestTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            EditText interest = (EditText) findViewById(R.id.interest_edit_text);
            EditText maxPrice = (EditText) findViewById(R.id.price_edit_text);

            itemOfInterest = interest.getText().toString();
            price = Double.parseDouble(maxPrice.getText().toString());

            HttpClient httpclient;
            HttpPost httppost;
            ArrayList<NameValuePair> postParameters;
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://teamkevin.me/Friends/Add");
            /*

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
                if (line.contains("success"))
                    return true;
            } catch (IOException e) {
                Log.d("HTTP post error", "" + e.getMessage());
            }
            */
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //mAddFriendTask = null;

            if (success) {
                // Success
            } else {
                // Display friend wasn't added toast
            }
        }

        @Override
        protected void onCancelled() {
            //mAddFriendTask = null;
        }
    }
}
