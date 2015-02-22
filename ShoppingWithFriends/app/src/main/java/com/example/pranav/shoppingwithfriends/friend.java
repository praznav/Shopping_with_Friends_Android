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
import android.view.View;

/**
 * Created by Pranav on 2/22/2015.
 */
public class Friend extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
    }

    public void onReturnPress(View view) {
        // Dismiss the spinner if it is open and finish the activity
        finish();
    }
}
