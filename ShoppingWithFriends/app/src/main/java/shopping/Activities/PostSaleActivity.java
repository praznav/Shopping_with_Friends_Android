package shopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import shopping.Controller.PostSaleController;
import shopping.R;
import shopping.View.PostSaleView;


public class PostSaleActivity extends ActionBarActivity implements PostSaleView{

    private PostSaleController cont;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("location: ", "yoyoyo");
        setContentView(R.layout.activity_post_sale);
        cont = new PostSaleController(this);

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


    /**
     * Gets the text in the item edit text
     * @return The string found in the item edit text
     */
    public String getItem()
    {
        return ((EditText)(findViewById(R.id.item_edit_text))).getText().toString();
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

    public String getLocation()
    {
        return ((EditText)(findViewById(R.id.location_edit_text))).getText().toString();
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
        finish();
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
     * Clears the fields for new entries
     */
    public void clearFields()
    {
        ((EditText)(findViewById(R.id.item_edit_text))).setText("");
        ((EditText)(findViewById(R.id.price_edit_text))).setText("");
        ((EditText)(findViewById(R.id.location_edit_text))).setText("");

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
    /**
     * Method called when the "REGISTER INTEREST" button is clicked
     * @param view current view
     */
    public void onButtonPress(View view) {
        Intent intent = new Intent(PostSaleActivity.this.getApplicationContext(),MapsActivity.class);
        EditText location = (EditText) findViewById(R.id.location_edit_text);
        intent.putExtra("address", location.getText().toString());
        startActivity(intent);
    }

}
