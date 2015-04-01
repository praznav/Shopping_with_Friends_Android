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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import shopping.R;


/**
 * Created by Pranav on 2/3/2015.
 */
public class RegistrationActivity extends Activity {
    /** UserRegisterTask used to register a user. Executes on Register press */
    private UserRegisterTask mRegisterTask = null;
    /** ProgressDialog shows on screen when the registration is loading */
    private ProgressDialog mSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.registration);

    }

    /**
     * Called whenever the user presses the return button.
     * @param view The view of the button that was pressed.
     */
    public void onReturnPress(View view) {
        // Dismiss the spinner if it is open and finish the activity
        onLoadingComplete();
        finish();
    }

    /**
     * Called whenever the user presses the register button.
     * @param view The view of the button that was pressed.
     */
    public void onRegisterPress(View view) {
        // If we are currently registering the user, return
        if (mRegisterTask != null) {
            return;
        }

        // Get the registration fields and reset the errors on each EditText box
        TextView emailIn = (TextView) findViewById(R.id.emailEditText);
        emailIn.setError(null);
        TextView usernameIn = (TextView) findViewById(R.id.usernameEditText);
        usernameIn.setError(null);
        TextView passwordIn = (TextView) findViewById(R.id.passwordEditText);
        passwordIn.setError(null);
        TextView confirmIn = (TextView) findViewById(R.id.confirmEditText);
        confirmIn.setError(null);
        TextView firstNameIn = (TextView) findViewById(R.id.firstNameEditText);
        firstNameIn.setError(null);
        TextView lastNameIn = (TextView) findViewById(R.id.lastNameEditText);
        lastNameIn.setError(null);
        String password = passwordIn.getText().toString();
        String confirm = confirmIn.getText().toString();
        if (!(password.equals(confirm) ) ) {
            // If the passwords do not match, show an error and let the user know before ever trying
            // to contact the server
            passwordIn.setError("Password does not match confirmation password.");
            confirmIn.setError("Confirmation password does not match password.");
            Toast.makeText(getApplicationContext(), "Your password and confirmation password do not match. Please correct them and try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        String firstName = firstNameIn.getText().toString();
        String lastName = lastNameIn.getText().toString();
        String email = emailIn.getText().toString();
        String username = usernameIn.getText().toString();

        // Create a new registration async task using the given registration fields and execute it
        mRegisterTask = new UserRegisterTask(username, email, password, firstName, lastName);
        mRegisterTask.execute((Void) null);
    }

    /**
     * Private helper function to create the ProgressDialog spinner and show it when we start
     * registering the user.
     */
    private void onLoadingBegin() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setMessage("Registering user...");
        mSpinner.show();
    }

    /**
     * Private helper function to dismiss and destroy the ProgressDialog spinner when we are done
     * with it
     */
    private void onLoadingComplete() {
        if (mSpinner != null) {
            mSpinner.dismiss();
            mSpinner = null;
        }
    }

    /**
     * User registration async task. Runs in the background and handles sending and receiving a
     * response from the backend server.
     */
    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUsername;
        private final String mEmail;
        private final String mPassword;
        private final String mFirstName;
        private final String mLastName;

        /**
         * Creates a new UserRegisterTask.
         * @param username The username the user requested.
         * @param email The email the user entered.
         * @param password The password the user entered.
         * @param firstName The first name the user entered.
         * @param lastName The last name the user entered.
         */
        UserRegisterTask(String username, String email, String password, String firstName, String lastName) {
            mUsername = username;
            mEmail = email;
            mPassword = password;
            mFirstName = firstName;
            mLastName = lastName;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Set up the HTTP post request
            String url = "http://teamkevin.me/Users/Register";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            // Add the post parameters to the request
            List<NameValuePair> parameters = new ArrayList<>(5);
            parameters.add(new BasicNameValuePair("username", mUsername));
            parameters.add(new BasicNameValuePair("password", mPassword));
            parameters.add(new BasicNameValuePair("email", mEmail));
            parameters.add(new BasicNameValuePair("firstName", mFirstName));
            parameters.add(new BasicNameValuePair("lastName", mLastName));
            try {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error encoding POST parameters");
                System.out.println(e.getMessage());
            }


            HttpResponse response;
            try {
                // Execute the POST request and save the response
                response = client.execute(post);

                // Read the full response and create a string out of it that can be parsed with
                // an XML parser
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                try {
                    // Create a new DomDocument out of the XML
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(sb.toString()));
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(is);

                    // Try and get the status from the response
                    NodeList statusList = doc.getElementsByTagName("status");
                    if (statusList.getLength() < 1) {
                        return false;
                    }
                    Element statusElement = (Element) statusList.item(0);
                    String status = ((CharacterData) statusElement.getFirstChild()).getData();

                    switch (status) {
                        case "success":
                            // If the status was success, registration was too
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // Show a toast to let the user know
                                    Toast.makeText(getApplicationContext(), "User successfully registered. Please login with your new user now.", Toast.LENGTH_LONG).show();
                                }
                            });
                            return true;
                        case "taken": {
                            // Username already taken, set the username error appropriately and show a
                            // toast to let the user know
                            Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                            final String usernameError = ((CharacterData) messageElement.getFirstChild()).getData();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    EditText usernameText = (EditText) findViewById(R.id.usernameEditText);
                                    usernameText.setError(usernameError);
                                    Toast.makeText(getApplicationContext(), usernameError, Toast.LENGTH_LONG).show();
                                }
                            });
                            return false;
                        }
                        case "error": {
                            // Server error occurred, show a toast with the contents of the message
                            Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                            final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), serverError, Toast.LENGTH_LONG).show();
                                }
                            });
                            return false;
                        }
                        case "invalid":
                            // One of the fields the user entered was invalid, go through each one
                            // and check for validation errors
                            NodeList usernameErrors = doc.getElementsByTagName("usernameErrors");
                            if (usernameErrors.getLength() > 0) {
                                // There were username errors
                                NodeList usernameChildren = usernameErrors.item(0).getChildNodes();
                                for (int i = 0; i < usernameChildren.getLength(); i++) {
                                    if (usernameChildren.item(i) instanceof Element) {
                                        final String usernameError = usernameChildren.item(i).getLastChild().getTextContent().trim();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EditText usernameText = (EditText) findViewById(R.id.usernameEditText);
                                                usernameText.setError(usernameError);
                                            }
                                        });
                                    }
                                }
                            }

                            NodeList emailErrors = doc.getElementsByTagName("emailErrors");
                            if (emailErrors.getLength() > 0) {
                                // There were email errors
                                NodeList emailChildren = emailErrors.item(0).getChildNodes();
                                for (int i = 0; i < emailChildren.getLength(); i++) {
                                    if (emailChildren.item(i) instanceof Element) {
                                        final String emailError = emailChildren.item(i).getLastChild().getTextContent().trim();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EditText emailText = (EditText) findViewById(R.id.emailEditText);
                                                emailText.setError(emailError);
                                            }
                                        });
                                    }
                                }
                            }

                            NodeList passwordErrors = doc.getElementsByTagName("passwordErrors");
                            if (passwordErrors.getLength() > 0) {
                                // There were password errors
                                NodeList passwordChildren = passwordErrors.item(0).getChildNodes();
                                for (int i = 0; i < passwordChildren.getLength(); i++) {
                                    if (passwordChildren.item(i) instanceof Element) {
                                        final String passwordError = passwordChildren.item(i).getLastChild().getTextContent().trim();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EditText passwordText = (EditText) findViewById(R.id.passwordEditText);
                                                passwordText.setError(passwordError);
                                            }
                                        });
                                    }
                                }
                            }

                            NodeList firstNameErrors = doc.getElementsByTagName("firstNameErrors");
                            if (firstNameErrors.getLength() > 0) {
                                // There were first name errors
                                NodeList firstNameChildren = firstNameErrors.item(0).getChildNodes();
                                for (int i = 0; i < firstNameChildren.getLength(); i++) {
                                    if (firstNameChildren.item(i) instanceof Element) {
                                        final String firstNameError = firstNameChildren.item(i).getLastChild().getTextContent().trim();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EditText firstNameText = (EditText) findViewById(R.id.firstNameEditText);
                                                firstNameText.setError(firstNameError);
                                            }
                                        });
                                    }
                                }
                            }

                            NodeList lastNameErrors = doc.getElementsByTagName("lastNameErrors");
                            if (lastNameErrors.getLength() > 0) {
                                // There were last name errors
                                NodeList lastNameChildren = lastNameErrors.item(0).getChildNodes();
                                for (int i = 0; i < lastNameChildren.getLength(); i++) {
                                    if (lastNameChildren.item(i) instanceof Element) {
                                        final String lastNameError = lastNameChildren.item(i).getLastChild().getTextContent().trim();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                EditText lastNameText = (EditText) findViewById(R.id.lastNameEditText);
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
                        default:
                            // The server response was unrecognized, let the user know
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Unrecognized server response.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return false;
                    }
                } catch (ParserConfigurationException e) {
                    Log.e("XML Exception", "Caught a parser configuration exception while creating the document builder", e);
                } catch (SAXException e) {
                    Log.e("XML Exception", "Caught a SAX exception while parsing the XML response", e);
                }
                return true;
            } catch (IOException e){
                System.out.println("IO Exception");
                System.out.println(e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            onLoadingBegin();
        }

        @Override
        protected void onPostExecute(final Boolean status) {
            // Dismiss the ProgressDialog and destroy the async task
            onLoadingComplete();
            mRegisterTask = null;
            if (status) {
                // If we registered successfully, finish the activity
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            // If we cancelled loading, dismiss the ProgressDialog and destroy the async task
            onLoadingComplete();
            mRegisterTask = null;
        }
    }

}
