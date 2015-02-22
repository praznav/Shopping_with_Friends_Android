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

package com.example.pranav.shoppingwithfriends;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Pranav on 2/9/2015.
 */
public class Processor extends Application{
    ArrayList<Person> allUsers;
    public  Processor() {
        allUsers = new ArrayList<Person>();
        allUsers.add(new User("test", "test", "test", "test", "test@gmail.com"));
    }

    public void addPerson(Person a) {
        allUsers.add(a);
    }

    public boolean isRegistered(Person a) {
        for (Person b : allUsers) {
            if (b.getUsername().equals(a.getUsername())) {
                if (b.getPassword().equals(a.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String[] getPeeps() {
        String[] peeps = new String[allUsers.size()];
        for (int i = 0; i < allUsers.size(); i++) {
            Person b = allUsers.get(i);
            String peep = b.getUsername();
            peep = peep + ":";
            peep = peep + b.getPassword();
            peeps [i] = peep;
        }
        return peeps;
    }
}
