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

public class User extends Person {
    /** numSales is a growing counter of the sales performed by the user */
	private int numSales;
    /** rating is an average of all the ratings given by all friends */
    private int rating;

	public User(String fName, String lName, String username, String password, String emailAddress) {
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}
}
