package com.example.pranav.shoppingwithfriends;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

public class FriendsList extends ActionBarActivity {
    private ListView mFriendsList;
    private ArrayList<String> friendsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        SharedPreferences prefs = getSharedPreferences("USER_LOGIN_INFO", 0);
        final String username = prefs.getString("Username", "\n");
        final String password = prefs.getString("Password", "\n");
        friendsList.add(username);
        friendsList.add(password);
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");
        friendsList.add("1");

        mFriendsList = (ListView) findViewById(R.id.friends_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friendsList);
        mFriendsList.setAdapter(adapter);


    }

    public void addFriend(View v) {
        EditText user = (EditText) findViewById(R.id.friend_username_edit_text);
        EditText email = (EditText) findViewById(R.id.friend_email_edit_text);

        String friendUsername = user.getText().toString();
        String friendEmail = email.getText().toString();

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            Log.d("test", "1");
            HttpGet httpget = new HttpGet("http://teamkevin.me/Friends/List?username=" + "user" + "&password=" + "pass");
            Log.d("test", "2");
            HttpResponse response = client.execute(httpget);
            Log.d("test", "3");
            String test = response.toString();
            Log.d("yuanhanyuanhanyuanhanyuanhanyuanhan",test);
            Log.d("test", "4");
        } catch (Exception e) {
            Log.d("https", "a" + e.getMessage());
            Log.d("test", "5");
        }

        Log.d("test", "6");
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
}