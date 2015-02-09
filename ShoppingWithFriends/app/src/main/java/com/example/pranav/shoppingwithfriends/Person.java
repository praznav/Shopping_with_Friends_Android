package CS2340;

public abstract class Person {
	protected final int DEFAULT_NUM_FRIENDS = 5;
	protected String fName;
	protected String lName;
	protected String username;
	protected String password;
	protected String emailAddress;
	// How will rating work?

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
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

	public boolean isPasswordValid(String password) {
		return (this.password.equals(password));
	}
}