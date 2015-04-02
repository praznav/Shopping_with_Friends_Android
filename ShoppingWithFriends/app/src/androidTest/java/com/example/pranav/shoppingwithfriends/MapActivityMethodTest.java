package com.example.pranav.shoppingwithfriends;

import android.content.Intent;
import android.location.Address;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;

import junit.framework.TestCase;

import shopping.Activities.MapsActivity;
import shopping.Activities.PostSaleActivity;

/**
 * Created by Pranav on 3/31/2015.
 */
public class MapActivityMethodTest extends ActivityInstrumentationTestCase2<MapsActivity> {

    MapsActivity activity;
    private RenamingDelegatingContext context = null;

    public MapActivityMethodTest () {
        super (MapsActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();

    }


    public void testEmpty() {
        String nothing = "";
        Address answer = activity.getLocationFromAddress(nothing);
        assertEquals(answer, null);
        System.out.println("this one is done");
    }

    public void testGeorgia() {
        String Georgia = "Georgia, USA";
        Address answer = activity.getLocationFromAddress(Georgia);
        if (answer != null) {
            assertEquals(answer.getCountryCode(), "US");
            assertEquals(answer.getLatitude(), 32.6781248);
            assertEquals(answer.getLongitude(), -83.2229758);
        }

    }


    public void testIreland() {
        String ireland = "Ireland";
        Address answer = activity.getLocationFromAddress(ireland);
        if (answer != null) {
            assertEquals(answer.getCountryCode(), "US");
            assertEquals(answer.getLatitude(), 53.3442);
            assertEquals(answer.getLongitude(), 6.2675);
        }

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
