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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import shopping.R;

public class Friend extends Activity{
    /** user's username */
    private String username;
    /** user's password */
    private String password;

    private String friendsUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        friendsUsername = getIntent().getStringExtra("friendsUsername");
        String friendFirstName = getIntent().getStringExtra("friendFirstName");
        String friendLastName = getIntent().getStringExtra("friendLastName");
        String friendEmail = getIntent().getStringExtra("friendEmail");
        String friendRating = getIntent().getStringExtra("friendRating");
        String friendReportCount = getIntent().getStringExtra("friendReportCount");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        TextView title = (TextView) findViewById(R.id.FriendNameTitle);
        TextView username = (TextView) findViewById(R.id.FriendUsername);
        TextView email = (TextView) findViewById(R.id.FriendEmail);
        TextView rating = (TextView) findViewById(R.id.FriendRating);
        TextView reports = (TextView) findViewById(R.id.FriendReports);

        title.setText(friendFirstName + " " + friendLastName);
        username.setText(friendsUsername);
        email.setText(friendEmail);
        rating.setText(friendRating);
        reports.setText(friendReportCount);

    }

    /**
     * Method to call when the "RETURN" button is clicked
     * @param view current view
     */
    public void onReturnPress(View view) {
        // Dismiss the spinner if it is open and finish the activity
        finish();
    }

    /**
     * Method to call when the "REMOVE" button is clicked
     * @param view current view
     */
    public void onRemovePress (View view) {
        removeFriend remover = new removeFriend();
        remover.execute((Void) null);
    }

    public class removeFriend extends AsyncTask<Void, Void, Boolean> {

        String line = "";

        removeFriend() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // put get request here with username, password, friend username,
                final String url = "http://teamkevin.me/Friends/Remove";
                DefaultHttpClient client = new DefaultHttpClient();
                HttpDelete httpDel = new HttpDelete(url + "?username=" + username + "&password=" + password + "&friendUsername=" + friendsUsername);
                HttpResponse response = client.execute(httpDel);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null)
                    sb.append(line);
                line = sb.toString();
                rd.close();
            } catch (IOException e) {
                Log.d("Error", e.getMessage());
            }

            Log.d("LINE", line);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean Success) {
            //if (!Success) {
                // error message here
                // toast or something
            //}
        }
    }
}