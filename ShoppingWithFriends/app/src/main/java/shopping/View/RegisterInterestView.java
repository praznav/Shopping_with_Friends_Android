package shopping.View;

import android.content.Intent;
import android.view.View;

/**
 * Created by Keshanz on 3/2/2015.
 */
public interface RegisterInterestView {
    public void onConfirmClick(View v);
    public void onReturnClick(View v);
    public String getItem();
    public double getPrice();
    public String getUsername();
    public String getPassword();
    public void displayError(String error);
    public void startNewActivity(Intent i);
    public void finish();
}
