package shopping.View;

import android.content.Intent;
import android.view.View;

/**
 * Created by Nevin on 2/25/15.
 */
public interface LoginView {
    public void onLoginClick(View v);
    public void onRegisterClick(View v);
    public String getEmail();
    public String getPassword();
    public void displayError(String error);
    public void startNewActivity(Intent i);
    public void setSharedPreferences(String username, String password);
}
