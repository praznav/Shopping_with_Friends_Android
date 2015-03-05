package shopping.ServerConnection;

import java.util.List;

import shopping.Model.User;

/**
 * Created by Zach on 2/20/2015.
 */
public interface ServerConnection {
    boolean RegisterUser(User newUser) throws ConnectionFailedException, UsernameTakenException,
            InvalidDataException, InternalServerErrorException, UnrecognizedResponseException;
    User AuthenticateUser(String username, String password) throws ConnectionFailedException,
            InvalidCredentialsException, InternalServerErrorException;
    List<User> GetFriendsList(User myUser) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException;
    boolean AddFriend(User myUser, String friendUsername) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException,
            AlreadyFriendsException;
    boolean AddInterest(User myUser, String productName, double maxPrice) throws
            UserNotAuthorizedException, InternalServerErrorException, InvalidProductNameException,
            InvalidPriceException, InvalidUserException, RegisteredInterestAlreadyExistsException,
            UnrecognizedResponseException ;
    }
