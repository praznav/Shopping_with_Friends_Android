package com.example.pranav.shoppingwithfriends;

public class User extends Person {
	private int numSales;
    private int rating;

	public User(String fName, String lName, String username, String password, String emailAddress) {
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}
}
