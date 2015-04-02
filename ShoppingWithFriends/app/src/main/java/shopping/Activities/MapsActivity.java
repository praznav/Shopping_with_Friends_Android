package shopping.Activities;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import shopping.R;

/**
 * Created by Pranav on 3/26/2015.
 *
 * MapActivity is the inapp Google maps view
 */
public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // This is the location that is passed into this activity by the previous activity as an extra
    // this should be the location of the sale
    // this activity handles nulls
    String location = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        location = getIntent().getStringExtra("address");
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        Address place = getLocationFromAddress("3648 Post Oak Tritt rd.");
//        location = "3648 Post Oak Tritt rd.";
        Address place = getLocationFromAddress(location);
        try {

//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())).title("Sale"));
        } catch (Exception e) {
        }
    }

    /**
     * The purpose of this method is to take an address as a string and output a form
     * that we can use to get Latitude and Longitude
     *
     * @param strAddress        this is the address in string form
     * @return  an Address object of the address passed in
     */
    public Address getLocationFromAddress(String strAddress) {

        //try {
            Geocoder coder = new Geocoder(this);
/*        } catch (Exception e) {
            Geocoder coder = new Geocoder(MapsActivity.class.);
        }
        */
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if (address.equals("")) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            return location;
        } catch (Exception e) {
        }
        return null;
    }
}
