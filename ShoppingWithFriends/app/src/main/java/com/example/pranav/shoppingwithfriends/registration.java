package com.example.pranav.shoppingwithfriends;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by Pranav on 2/3/2015.
 */
public class registration extends Activity {

    private UserRegisterTask mRegisterTask = null;

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
        if (mRegisterTask != null) {
            return;
        }

        Log.d("HTTP", "Register pressed");
        TextView emailIn = (TextView) findViewById(R.id.editText);
        emailIn.setError(null);
        TextView usernameIn = (TextView) findViewById(R.id.editText2);
        usernameIn.setError(null);
        TextView passwordIn = (TextView) findViewById(R.id.editText3);
        passwordIn.setError(null);
        TextView confirmIn = (TextView) findViewById(R.id.editText4);
        confirmIn.setError(null);
        TextView firstNameIn = (TextView) findViewById(R.id.editText5);
        firstNameIn.setError(null);
        TextView lastNameIn = (TextView) findViewById(R.id.editText6);
        lastNameIn.setError(null);

        String password = passwordIn.getText().toString();
        String confirm = confirmIn.getText().toString();
        if (!(password.equals(confirm) ) ) {
            passwordIn.setError("Password does not match confirmation password.");
            confirmIn.setError("Confirmation password does not match password.");
            Toast.makeText(getApplicationContext(), "Your password and confirmation password do not match. Please correct them and try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        String firstName = firstNameIn.getText().toString();
        String lastName = lastNameIn.getText().toString();
        String email = emailIn.getText().toString();
        String username = usernameIn.getText().toString();

        mRegisterTask = new UserRegisterTask(username, email, password, firstName, lastName);
        mRegisterTask.execute((Void) null);

    // stuff to get rid of:
        // Person newPerson = new User(firstName, lastName, username, password, email);
        // Processor pro = ((Processor)getApplicationContext());
        // pro.addPerson(newPerson);
        // finish();
    }

    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUsername;
        private final String mEmail;
        private final String mPassword;
        private final String mFirstName;
        private final String mLastName;

        UserRegisterTask(String username, String email, String password, String firstName, String lastName) {
            mUsername = username;
            mEmail = email;
            mPassword = password;
            mFirstName = firstName;
            mLastName = lastName;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = "http://teamkevin.me/Users/Register";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            List<NameValuePair> parameters = new ArrayList<NameValuePair>(5);
            parameters.add(new BasicNameValuePair("username", mUsername));
            parameters.add(new BasicNameValuePair("password", mPassword));
            parameters.add(new BasicNameValuePair("email", mEmail));
            parameters.add(new BasicNameValuePair("firstName", mFirstName));
            parameters.add(new BasicNameValuePair("lastName", mLastName));

            try {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            } catch (UnsupportedEncodingException e) {
                System.out.println("encoding exception");
                System.out.println(e.getMessage());
            }
            HttpResponse response;
            HttpEntity entity = null;
            try {
                Log.d("https", "Executing post...");
                response = client.execute(post);
                Log.d("https", "Post executed");
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    Log.i("https", line);
                    sb.append(line);
                }
                Log.d("https", "Full xml output is: " + sb.toString());
                try {
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(sb.toString()));
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(is);
                    NodeList statusList = doc.getElementsByTagName("status");
                    if (statusList.getLength() < 1) {
                        return false;
                    }
                    Element statusElement = (Element) statusList.item(0);
                    Log.d("https", "Status is: " + ((CharacterData) statusElement.getFirstChild()).getData());
                    String status = ((CharacterData) statusElement.getFirstChild()).getData();
                    if (status.equals("success")) {
                        return true;
                    } else if (status.equals("taken")) {
                        Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                        final String usernameError = ((CharacterData) messageElement.getFirstChild()).getData();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                EditText usernameText = (EditText) findViewById(R.id.editText2);
                                usernameText.setError(usernameError);
                                Toast.makeText(getApplicationContext(), usernameError, Toast.LENGTH_LONG).show();
                            }
                        });
                        return false;
                    } else if (status.equals("error")) {
                        Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                        final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), serverError, Toast.LENGTH_LONG).show();
                            }
                        });
                        return false;
                    } else if (status.equals("invalid")) {
                        NodeList usernameErrors = doc.getElementsByTagName("usernameErrors");
                        if (usernameErrors.getLength() > 0) {
                           NodeList usernameChildren = usernameErrors.item(0).getChildNodes();
                           for (int i = 0; i < usernameChildren.getLength(); i++) {
                               if (usernameChildren.item(i) instanceof Element) {
                                   final String usernameError = usernameChildren.item(i).getLastChild().getTextContent().trim();
                                   runOnUiThread(new Runnable() {
                                       public void run() {
                                           EditText usernameText = (EditText) findViewById(R.id.editText2);
                                           usernameText.setError(usernameError);
                                       }
                                   });
                               }
                           }
                        }
                        NodeList emailErrors = doc.getElementsByTagName("emailErrors");
                        if (emailErrors.getLength() > 0) {
                            NodeList emailChildren = emailErrors.item(0).getChildNodes();
                            for (int i = 0; i < emailChildren.getLength(); i++) {
                                if (emailChildren.item(i) instanceof Element) {
                                    final String emailError = emailChildren.item(i).getLastChild().getTextContent().trim();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            EditText emailText = (EditText) findViewById(R.id.editText2);
                                            emailText.setError(emailError);
                                        }
                                    });
                                }
                            }
                        }
                        NodeList passwordErrors = doc.getElementsByTagName("passwordErrors");
                        if (passwordErrors.getLength() > 0) {
                            NodeList passwordChildren = passwordErrors.item(0).getChildNodes();
                            for (int i = 0; i < passwordChildren.getLength(); i++) {
                                if (passwordChildren.item(i) instanceof Element) {
                                    final String passwordError = passwordChildren.item(i).getLastChild().getTextContent().trim();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            EditText passwordText = (EditText) findViewById(R.id.editText3);
                                            passwordText.setError(passwordError);
                                        }
                                    });
                                }
                            }
                        }
                        NodeList firstNameErrors = doc.getElementsByTagName("firstNameErrors");
                        if (firstNameErrors.getLength() > 0) {
                            NodeList firstNameChildren = firstNameErrors.item(0).getChildNodes();
                            for (int i = 0; i < firstNameChildren.getLength(); i++) {
                                if (firstNameChildren.item(i) instanceof Element) {
                                    final String firstNameError = firstNameChildren.item(i).getLastChild().getTextContent().trim();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            EditText firstNameText = (EditText) findViewById(R.id.editText5);
                                            firstNameText.setError(firstNameError);
                                        }
                                    });
                                }
                            }
                        }
                        NodeList lastNameErrors = doc.getElementsByTagName("lastNameErrors");
                        if (lastNameErrors.getLength() > 0) {
                            NodeList lastNameChildren = lastNameErrors.item(0).getChildNodes();
                            for (int i = 0; i < lastNameChildren.getLength(); i++) {
                                if (lastNameChildren.item(i) instanceof Element) {
                                    final String lastNameError = lastNameChildren.item(i).getLastChild().getTextContent().trim();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            EditText lastNameText = (EditText) findViewById(R.id.editText6);
                                            lastNameText.setError(lastNameError);
                                        }
                                    });
                                }
                            }
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Your registration contains invalid data. Please check your entries and try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Unrecognized server response.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }
                } catch (Exception e) {

                    Log.d("Caught Exception", "Caught exception with message: " + e.getMessage());
                }
                return true;
            } catch (IOException e){
                System.out.println("IO Exception");
                System.out.println(e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean status) {
            Log.d("https", "Done executing...");
            mRegisterTask = null;
            if (status) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
        }
    }

}
