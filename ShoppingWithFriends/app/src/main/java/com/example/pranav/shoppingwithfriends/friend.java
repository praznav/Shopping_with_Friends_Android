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
package com.example.pranav.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Pranav on 2/22/2015.
 */
public class Friend extends Activity{
    String friendsUsername;
    String friendFirstName;
    String friendLastName;
    String friendEmail;
    String friendRating;
    String friendReportCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        friendsUsername = getIntent().getStringExtra("friendsUsername");
        friendFirstName = getIntent().getStringExtra("friendFirstName");
        friendLastName = getIntent().getStringExtra("friendLastName");
        friendEmail = getIntent().getStringExtra("friendEmail");
        friendRating = getIntent().getStringExtra("friendRating");
        friendReportCount = getIntent().getStringExtra("friendReportCount");


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

    public void onReturnPress(View view) {
        // Dismiss the spinner if it is open and finish the activity
        finish();
    }
}
