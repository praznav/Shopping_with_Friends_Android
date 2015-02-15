package com.example.pranav.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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


        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost();

        String password = passwordIn.getText().toString();
        String confirm = confirmIn.getText().toString();
        if (!(password.equals(confirm) ) ) {
            // insert fail condition here
        }
        String firstName = firstNameIn.getText().toString();
        String lastName = lastNameIn.getText().toString();
        String email = emailIn.getText().toString();
        String username = usernameIn.getText().toString();

        List<NameValuePair> parameters = new ArrayList<NameValuePair>(5);
        parameters.add(new BasicNameValuePair("param-1", username));
        parameters.add(new BasicNameValuePair("param-2", password));
        parameters.add(new BasicNameValuePair("param-3", email));
        parameters.add(new BasicNameValuePair("param-4", firstName));
        parameters.add(new BasicNameValuePair("param-5", lastName));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("encoding exception");
            System.out.println(e.getMessage());
        }
        HttpResponse response;
        HttpEntity entity = null;
        try {
            response = httpclient.execute(httppost);
            entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                Scanner s = new Scanner(instream).useDelimiter("\\A");

                try {
                    s.next();       // ie: get response and whaterver
                } finally {
                    instream.close();
                }
            }
        } catch (IOException e){
            System.out.println("IO Exception");
            System.out.println(e.getMessage());
        }




    // stuff to get rid of:
        Person newPerson = new User(firstName, lastName, username, password, email);
        Processor pro = ((Processor)getApplicationContext());
        pro.addPerson(newPerson);
        finish();
    }

}
