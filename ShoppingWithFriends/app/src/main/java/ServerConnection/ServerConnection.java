package ServerConnection;

import com.example.pranav.shoppingwithfriends.User;

import java.util.List;

/**
 * Created by Zach on 2/20/2015.
 */
public interface ServerConnection {
    User AuthenticateUser(String username, String password) throws ConnectionFailedException,
            InvalidCredentialsException, InternalServerErrorException;
    List<User> GetFriendsList(User myUser) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException;
    boolean AddFriend(User myUser, String friendUsername) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException,
            AlreadyFriendsException;
}
