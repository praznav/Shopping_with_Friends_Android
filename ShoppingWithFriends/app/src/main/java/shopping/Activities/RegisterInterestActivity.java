package shopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.NumberFormat;
import java.util.ArrayList;

import shopping.Controller.RegisterInterestController;
import shopping.R;
import shopping.View.RegisterInterestView;


public class RegisterInterestActivity extends ActionBarActivity implements RegisterInterestView{

    private RegisterInterestController cont;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);
        cont = new RegisterInterestController(this);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.credential_preference_string), Context.MODE_PRIVATE);
        username = prefs.getString("Username", "");
        password = prefs.getString("Password", "");


        final EditText txtEdit = (EditText) findViewById(R.id.price_edit_text);

        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    roundPrice();
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

    /**
     * Gets the text in the item edit text
     * @return The string found in the item edit text
     */
    public String getItem()
    {
        return ((EditText)(findViewById(R.id.interest_edit_text))).getText().toString();
    }

    /**
     * Gets the price found in the price edit text
     * @return The price converted to a double value
     */
    public double getPrice()
    {
        double d = -1;
        try
        {
             d = NumberFormat.getInstance().parse(((EditText)(findViewById(R.id.price_edit_text))).getText().toString()).doubleValue();
        }
        catch (Exception e)
        {
        }
        return d;
    }

    /**
     * Gets the current user's username
     * @return username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Gets the current user's password
     * @return current user's password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Handler for when confirm button is clicked. Calls controller's version
     * @param view current view
     */
    public void onConfirmClick(View view)
    {
        roundPrice();
        cont.onConfirmClick();
    }


    /**
     * Handler for when return button is clicked.  Calls controller's version
     * @param view current view
     */
    public void onReturnClick(View view)
    {
        cont.onReturnClick();
    }

    /**
     * Displays a toast
     * @param s the message to display
     */
    public void displayError(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Finishes the current activity
     */
    public void finish()
    {
        finish();
    }

    /**
     * Starts a new activity
     * @param i the intent of the new activity to start
     */
    public void startNewActivity(Intent i) {
        startActivity(i);
    }

    /**
     * Clears the fields for new entries
     */
    public void clearFields()
    {
        ((EditText)(findViewById(R.id.interest_edit_text))).setText("");
        ((EditText)(findViewById(R.id.price_edit_text))).setText("");
    }

    /**
     * Rounds the price field so that it is at a dollar value
     */
    private void roundPrice()
    {
        final EditText txtEdit = (EditText) findViewById(R.id.price_edit_text);

        double price = 0;
        try
        {
            price = NumberFormat.getInstance().parse(((EditText)(findViewById(R.id.price_edit_text))).getText().toString()).doubleValue();
        }
        catch (Exception e)
        {
        }
        price = (Math.round(price * 100))/100.0;
        txtEdit.setText("" + price, TextView.BufferType.EDITABLE);
    }

}
