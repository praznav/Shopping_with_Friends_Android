package com.example.pranav.shoppingwithfriends;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendsList extends ActionBarActivity {
    private Button mAddFriend;
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

        mAddFriend = (Button) findViewById(R.id.add_friend_button);
        mAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kevin", "1");

                Log.d("kevin", username);

                Log.d("kevin", password);
            }
        });
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
