package com.example.pranav.shoppingwithfriends;

import java.util.ArrayList;

/**
 * Created by Pranav on 2/9/2015.
 */
public class Processor {
    ArrayList<Person> allUsers;
    public  Processor() {
        allUsers = new ArrayList<Person>();
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
}
