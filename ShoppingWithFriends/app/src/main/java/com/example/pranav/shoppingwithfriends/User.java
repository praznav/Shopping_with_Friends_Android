package CS2340;

public class User extends Person {
	protected int numSales;
	protected Person[] friends;
	protected int numFriends;
	protected int rating;

	public User(String fName, String lName, String username, String password, String emailAddress) {
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		friends = new Person[DEFAULT_NUM_FRIENDS];
	}

	public void addFriend(Person friend) {
		friends[numFriends] = friend;
		numFriends++;
	}
}
