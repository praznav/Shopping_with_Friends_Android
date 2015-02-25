package ServerConnection;

import com.example.pranav.shoppingwithfriends.User;

import java.util.List;

/**
 * Created by Zach on 2/20/2015.
 */
public class AgaviServerConnection implements ServerConnection {
    private String mConnectionUrl = "http://teamkevin.me";

    private AgaviServerConnection() {
    }

    @Override
    public User AuthenticateUser(String username, String password) throws ConnectionFailedException,
            InvalidCredentialsException, InternalServerErrorException {
        return null;
    }

    @Override
    public List<User> GetFriendsList(User myUser) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException {
        return null;
    }

    @Override
    public boolean AddFriend(User myUser, String friendUsername) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException,
            AlreadyFriendsException {
        return false;
    }
}
