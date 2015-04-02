package shopping.Model;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import shopping.Controller.LoginController;

/**
 * Created by Kevin on 2/25/15.
 */
public class LoginModel {
    private String mUsername = "";
    private String mPassword = "";
    private LoginController cont;
    private String message;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    public LoginModel(LoginController c) {
        cont = c;
    }

    public LoginModel() { }

    /**
     * Sets the model's user data value
     * @param username The user to set
     */
    public void setUsername(String username) {
        mUsername = username;
    }

    /**
     * Sets the model's pass data value
     * @param pass The password to set
     */
    public void setPassword(String pass) {
        mPassword = pass;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public String attemptLogin() {
        message = "In Progress";

        if (TextUtils.isEmpty(mUsername)) {
            message = "Invalid username";
            return message;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword) || !isPasswordValid(mPassword)) {
            message = "Invalid password";
            return message;
        }

        mAuthTask = new UserLoginTask();
        try {
            mAuthTask.execute((Void) null).get();
        }
        catch(Exception e)
        {
        }
        return message;
    }

    /**
     * Determines if username is valid
     * @param username The username to evaluate
     * @return if it is valid
     */
    private boolean isUsernameValid(String username) {
        // Add conditions later
        return true;
    }

    /**
     * Determines if password is valid.  Valid passwords are at least length 3
     * @param password The password to evaluate
     * @return if it is valid
     */
    private boolean isPasswordValid(String password) {
        // Add conditions later
        return password.length() > 3;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://teamkevin.me/Users/Login?username=" + mUsername + "&password=" + mPassword);
                HttpResponse response = client.execute(httpget);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    Log.i("https", line);
                    if (line.contains("success"))
                    {
                        message = "Success";
                        return true;
                    }
                }

            } catch (Exception e) {
                Log.d("https", e.getMessage());
                return false;
            }
            message = "Incorrect username or password";
            Log.i("https", "No success");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}