package shopping.Model;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import shopping.Controller.LoginController;

/**
 * Created by Kevin on 2/25/15.
 */
public class LoginTask {
    private String mEmail = "";
    private String mPassword = "";
    private LoginController cont;
    private String message;

    public LoginTask(LoginController c) {
        cont = c;
        message = "";
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPassword(String pass) {
        mPassword = pass;
    }


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public String attemptLogin() {
        message = "In Progress";
        if (mAuthTask != null) {
            message = "Attempting log in";
            return message;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(mPassword) && !isPasswordValid(mPassword)) {
            message = "Invalid password.";
            return message;
        }

        // Check for a valid username
        if (TextUtils.isEmpty(mEmail)) {
            message = "Invalid email address.";
            return message;
        } else if (!isEmailValid(mEmail)) {
            message = "Invalid email address.";
            return message;
        }
        mAuthTask = new UserLoginTask();
        try {
            mAuthTask.execute((Void) null).get();
        }
        catch(Exception e)
        {
            message = "Timed out";
        }
        return message;
    }

    private boolean isEmailValid(String email) {
        // Add conditions later
        return true;
    }

    private boolean isPasswordValid(String password) {
        // Add conditions later
        return password.length() > 2;
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
                HttpGet httpget = new HttpGet("http://teamkevin.me/Users/Login?username=" + mEmail + "&password=" + mPassword);
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