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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import shopping.R;

public class FriendsListActivity extends Activity {
    /** ListView containing all the friends of the logged in User. attributes are clickable */
    private ListView mFriendsList;
    /** an ArrayList storing all the friends */
    private ArrayList<String> friendsList;
    /** GetFriendsTask executes on creation and gets friends */
    private GetFriendsTask mGetFriendsTask;
    /** mAddFriendTask executes when user clicks add friend. Uses fields to add friend. */
    private AddFriendTask mAddFriendTask;
    /** username and password are required for the database requests/posts */
    String username, password;
    /** adapter */
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_friends_list);
        friendsList = new ArrayList<String>();

        // Gets username and password for current user from shared preferences
        SharedPreferences prefs = getSharedPreferences(getString(R.string.credential_preference_string), Context.MODE_PRIVATE);
        username = prefs.getString("Username", "");
        password = prefs.getString("Password", "");
        Log.d("Username", "Got username of: " + username);
        Log.d("Password", "Got password of: " + password);

        // Loads friend list
        mGetFriendsTask = new GetFriendsTask(username, password);
        mGetFriendsTask.execute((Void) null);

        // Sets list view for friends list
        mFriendsList = (ListView) findViewById(R.id.friends_list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, friendsList);
        mFriendsList.setAdapter(adapter);

        mFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String things = friendsList.get(position);
                String friendusername = things.substring(things.indexOf(" --- ") + 5);


                getFriendInfo getter = new getFriendInfo(friendusername);
                getter.execute((Void) null);



            }
        });
    }

    /**
     * Creates the new adapter for the list view and fills its contents
     */
    public void thatMethod() {
        adapter = null;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, friendsList);
        adapter.notifyDataSetChanged();
        mFriendsList.setAdapter(adapter);
    }

    /**
     * Method called when "ADD FRIEND" button is clicked
     * Adds friend by sending request to server and reloads
     * list view to update the friends list
     * @param v current view
     */
    public void addFriend(View v) {
        mAddFriendTask = new AddFriendTask(username, password);
        mAddFriendTask.execute((Void) null);
        mGetFriendsTask = new GetFriendsTask(username, password);
        mGetFriendsTask.execute((Void) null);
        adapter = null;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, friendsList);
        adapter.notifyDataSetChanged();
        mFriendsList.setAdapter(adapter);
    }

    /**
     * Method called when "RETURN" button is clicked
     * @param v current view
     */
    public void goBack(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_list, menu);
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


    public class getFriendInfo extends AsyncTask<Void, Void, Boolean> {

        private final String friendusername;
        private String line;
        boolean success;

        /**
         * Method to set which friend we are getting info for
         * @param in The friend to get info for
         */
        getFriendInfo(String in) {
            friendusername = in;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            line = "";
            try {
                // put get request here with username, password, friendusername,
                final String url = "http://teamkevin.me/Friends/Get";
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url + "?username=" + username + "&password=" + password + "&friendUsername=" + friendusername);
                HttpResponse response = client.execute(httpget);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null)
                    sb.append(line);
                line = sb.toString();
                rd.close();
            } catch (IOException e) {
                Log.d("Error", e.getMessage());
            }
            Log.d("LINE", line.substring(line.indexOf("response") + 11));
            if (line.contains("<satus>success</satus>")) {
                success = true;
                line = line.substring(line.indexOf("<satus>success</satus>") + 22);
                Log.d("LINE",line);
                return true;
            } else {
                success = false;
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean Success) {
            if (Success == true) {
                // parse line and get all the values
                String friendsUsername = line.substring(line.indexOf("<friendUsername>") + 16, line.indexOf("</friendUsername>"));
                String friendFirstName = line.substring(line.indexOf("<friendFirstName>") + 17, line.indexOf("</friendFirstName>"));
                String friendLastName = line.substring(line.indexOf("<friendLastName>") + 16, line.indexOf("</friendLastName>"));
                String friendEmail = line.substring(line.indexOf("<friendEmail>") + 13, line.indexOf("</friendEmail>"));
                String friendRating = line.substring(line.indexOf("<friendRating>") + 14, line.indexOf("</friendRating>"));
                String friendReportCount = line.substring(line.indexOf("<friendReportCount>") + 19, line.indexOf("</friendReportCount>"));

                Intent i = new Intent(FriendsListActivity.this, Friend.class);
                i.putExtra("friendsUsername", friendsUsername);
                i.putExtra("friendFirstName", friendFirstName);
                i.putExtra("friendLastName", friendLastName);
                i.putExtra("friendEmail", friendEmail);
                i.putExtra("friendRating", friendRating);
                i.putExtra("friendReportCount", friendReportCount);
                i.putExtra("username", username);
                i.putExtra("password", password);

                startActivity(i);
            } else {
                // error message here
                // toast or something
            }
        }
    }


    /**
     * Asynchronous task that loads friends list from server
     */
    public class GetFriendsTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;

        /**
         * Constructor for task
         * @param email current user's email
         * @param password current user's password
         */
        GetFriendsTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                friendsList = new ArrayList<String>();
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://teamkevin.me/Friends/List?username=" + username + "&password=" + password);
                HttpResponse response = client.execute(httpget);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null)
                    sb.append(line);
                line = sb.toString();
                rd.close();

                // Parse XML response
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader((line))));

                NodeList nodeList = document.getDocumentElement().getChildNodes();

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeName().equals("status"))
                        continue;
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node cNode = childNodes.item(j);
                        if (cNode.getLastChild() != null) {
                            String content = cNode.getLastChild().getTextContent().trim();
                            if (cNode.getNodeName().equals("friend")) {
                                int parenthesisPosition = content.indexOf("(");
                                String name = content.substring(0, parenthesisPosition);
                                String username = content.substring(parenthesisPosition + 1, content.length() - 1);
                                String friendsListEntry = name + " --- " + username;
                                friendsList.add(friendsListEntry);
                                Log.d("In XML parsing", friendsListEntry);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("https", "" + e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mGetFriendsTask = null;
            if (success) {
                thatMethod();
                // Display toast
            } else {
                // Display toast
            }
        }

        @Override
        protected void onCancelled() {
            mGetFriendsTask = null;
        }
    }

    /**
     * Asynchronous task that adds friend by communicating with server
     */
    public class AddFriendTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;
        private String friendUsername;

        /**
         * Constructor to create add friend task
         * @param email current user's email
         * @param password current user's password
         */
        AddFriendTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            friendsList = new ArrayList<String>();
            EditText user = (EditText) findViewById(R.id.friend_username_edit_text);

            friendUsername = user.getText().toString();

            HttpClient httpclient;
            HttpPost httppost;
            ArrayList<NameValuePair> postParameters;
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://teamkevin.me/Friends/Add");

            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", username));
            postParameters.add(new BasicNameValuePair("password", password));
            postParameters.add(new BasicNameValuePair("friendUsername", friendUsername));

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
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAddFriendTask = null;

            if (success) {
                // Success
            } else {
                // Display friend wasn't added toast
            }
        }

        @Override
        protected void onCancelled() {
            mAddFriendTask = null;
        }
    }
}