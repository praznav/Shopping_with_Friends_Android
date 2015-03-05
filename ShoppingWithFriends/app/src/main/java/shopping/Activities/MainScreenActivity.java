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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import shopping.R;

public class MainScreenActivity extends Activity {
    /** mVeiwFriends is a button that redirects to the FriendsList page */
    private Button mViewFriends;
    /** mLogout is a button that logs out the user and returns him to the login screen*/
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mViewFriends = (Button) findViewById(R.id.view_friends_button);
        mLogout = (Button) findViewById(R.id.logout_button);
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
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
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
}