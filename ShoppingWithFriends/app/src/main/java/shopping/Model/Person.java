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

package shopping.Model;

public abstract class Person {
	protected final int DEFAULT_NUM_FRIENDS = 5;
	protected String fName;
	protected String lName;
	protected String username;
	protected String password;
	protected String emailAddress;
	// How will rating work?

    /**
     * Gets the first name for the person
     * @return The first name for the person
     */
	public String getFirstName() {
		return fName;
	}

    /**
     * Sets the first name for the person
     * @param fName The new first name for the person
     */
	public void setFirstName(String fName) {
		this.fName = fName;
	}

    /**
     * Gets the last name for the person
     * @return The last name for the person
     */
	public String getLastName() {
		return lName;
	}

    /**
     * Sets the last name for the person
     * @param lName The new last name for the person
     */
	public void setLastName(String lName) {
		this.lName = lName;
	}

    /**
     * Gets the username for the person
     * @return The username for the person
     */
	public String getUsername() {
		return username;
	}

    /**
     * Sets the username for the person
     * @param username The new username for the person
     */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * Gets the password for the person
     * @return The password for the person
     */
	public String getPassword() {
		return password;
	}

    /**
     * Sets the password for the person
     * @param password The new password for the person
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Gets the email address for the person
     * @return The email address for the person
     */
    public String getEmailAddress() { return emailAddress; }

    /**
     * Sets the email address for the person
     * @param emailAddress The new email address for the person
     */
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    /**
     * Checks to see if a given password matches the set password
     * @param password The password to check against
     * @return Whether or not the passwords match
     */
	public boolean isPasswordValid(String password) {
		return (this.password.equals(password));
	}


}