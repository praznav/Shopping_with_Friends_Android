package com.example.pranav.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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

    public void onRegisterPress(View view) {
        TextView firstNameIn = (TextView) findViewById(R.id.editText5);
        TextView lastNameIn = (TextView) findViewById(R.id.editText6);
        TextView emailIn = (TextView) findViewById(R.id.editText);
        TextView usernameIn = (TextView) findViewById(R.id.editText2);
        TextView passwordIn = (TextView) findViewById(R.id.editText3);
        TextView confirmIn = (TextView) findViewById(R.id.editText4);

        String password = passwordIn.getText().toString();
        String confirm = confirmIn.getText().toString();
        if (!(password.equals(confirm) ) ) {
            // insert fail condition here
        }
        String firstName = firstNameIn.getText().toString();
        String lastName = lastNameIn.getText().toString();
        String email = emailIn.getText().toString();
        String username = usernameIn.getText().toString();

        Person newPerson = new User(firstName, lastName, username, password, email);

    }

}
