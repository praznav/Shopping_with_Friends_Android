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

	public String getFirstName() {
		return fName;
	}
	public void setFirstName(String fName) {
		this.fName = fName;
	}
	public String getLastName() {
		return lName;
	}
	public void setLastName(String lName) {
		this.lName = lName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

	public boolean isPasswordValid(String password) {
		return (this.password.equals(password));
	}


}