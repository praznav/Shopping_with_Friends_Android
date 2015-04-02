package shopping;

import android.util.Log;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import shopping.Controller.LoginController;
import shopping.Model.LoginModel;

/**
 * Created by Kevin on 4/1/2015.
 */
public class AttemptLoginTest extends TestCase {

    public void testNormalRun()
    {
        LoginModel m = new LoginModel();
        m.setUsername("Kevin");
        m.setPassword("password");
        String message = m.attemptLogin();
        assertFalse(message.equals("In Progress"));
    }

    public void testInvalidUsername()
    {
        LoginModel m = new LoginModel();
        m.setUsername("");
        m.setPassword("password");
        String message = m.attemptLogin();
        assertTrue(message.equals("Invalid username"));
    }

    public void testInvalidPassword()
    {
        LoginModel m = new LoginModel();
        m.setUsername("Kevin");
        m.setPassword("");
        String message = m.attemptLogin();
        assertTrue(message.equals("Invalid password"));
    }
}
