/*
    **
    * @version 1.0
    * @team kevin
    * @teamNumber 1
    * @author Pranav Shenoy
    * @author Kevin Han
    * @author Zachary Peterson
    * @author Neil Vohra
 */


package shopping.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import shopping.Controller.MainScreenController;
import shopping.Model.Sale;
import shopping.R;
import shopping.View.MainScreenView;

public class MainScreenActivity extends Activity implements MainScreenView {
    /** mVeiwFriends is a button that redirects to the FriendsList page */
    private Button mViewFriends;
    /** mLogout is a button that logs out the user and returns him to the login screen*/
    private Button mLogout;
    /** ListView containing all the Sales pertaining to the logged in User. attributes are clickable */
    private ListView mSalesList;

    List<Sale> salesList;

    private MainScreenController cont;

    /** username and password are required for the database requests/posts */
    String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        salesList = null;
        cont = new MainScreenController(this);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.credential_preference_string), Context.MODE_PRIVATE);
        username = prefs.getString("Username", "");
        password = prefs.getString("Password", "");

        mViewFriends = (Button) findViewById(R.id.view_friends_button);
        mLogout = (Button) findViewById(R.id.logout_button);
        mSalesList = (ListView) findViewById(R.id.sales_list_view);

        cont.refreshList();
        /**
         * click listener for the SalersLIst
         * a click takes you to the map which shows the location
         */
        mSalesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Sale thing = salesList.get(position);
                Intent intent = new Intent(MainScreenActivity.this.getApplicationContext(),MapsActivity.class);
                intent.putExtra("address", thing.getLocation());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Test", "Returned to!");
        cont.refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    @Override
    public void displaySales(List<Sale> salesList)
    {
        this.salesList = salesList;
        List<String> sales = new ArrayList<String>();
        for(int i = 0; i < salesList.size(); i++)
        {
            Sale s = salesList.get(i);
            Log.d("Test", s.getProduct());
            sales.add(s.getUser() + " found " + s.getProduct() + " for " + s.getPriceFound() + " at " + s.getLocation() + ". Your max price was " + s.getPriceRequested());
        }
        // Sets list view for friends list
        ListView mSalesList = (ListView) findViewById(R.id.sales_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sales);
        mSalesList.setAdapter(adapter);
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }
    /**
     * Method called when the "VIEW FRIENDS" button is clicked
     * @param view current view
     */
    public void viewFriends(View view) {
        Intent intent = new Intent(MainScreenActivity.this.getApplicationContext(), FriendsListActivity.class);
        startActivity(intent);
    }

    /**
     * Method called when the "RETURN" button is clicked
     * @param view current view
     */
    public void onReturnPress(View view)
    {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.credential_preference_string), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Username", "");
        editor.putString("Password", "");
        editor.apply();
        finish();
    }

    /**
     * Method called when the "REGISTER INTEREST" button is clicked
     * @param view current view
     */
    public void onRegisterInterestPress(View view) {
        Intent intent = new Intent(MainScreenActivity.this.getApplicationContext(),RegisterInterestActivity.class);
        startActivity(intent);
    }

    /**
     * Handler for when "POST SALE" is clicked
     * @param view current view
     */
    public void onPostSalePress(View view) {
        Intent intent = new Intent(MainScreenActivity.this.getApplicationContext(), PostSaleActivity.class);
        startActivity(intent);
    }
}