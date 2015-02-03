package com.example.pranav.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by Pranav on 2/3/2015.
 */
public class registration extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Button reg = (Button) findViewById(R.id.regButton);
        Button ret = (Button) findViewById(R.id.retButton);
    }

    public void onReturnPress(View view)
    {
        finish();
    }


}
