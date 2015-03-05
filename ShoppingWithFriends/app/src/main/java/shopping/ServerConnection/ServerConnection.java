package shopping.ServerConnection;

import java.util.List;

import shopping.Model.User;

/**
 * Interface for the app server connection. Contains all of the methods for contacting the server
 * and performing queries or update actions.
 *
 * @author Zachary Peterson
 */
public interface ServerConnection {
    /**
     * Attempts to register a new user.
     * @param newUser The user object to attempt to register.
     * @return Success of the registration.
     * @throws ConnectionFailedException
     * @throws UsernameTakenException
     * @throws InvalidDataException
     * @throws InternalServerErrorException
     * @throws UnrecognizedResponseException
     */
    boolean RegisterUser(User newUser) throws ConnectionFailedException, UsernameTakenException,
            InvalidDataException, InternalServerErrorException, UnrecognizedResponseException;

    /**
     * Attempts to authenticate the given username and password.
     * @param username The username to authenticate with.
     * @param password The password to authenticate with.
     * @return The user that was successfully authenticated, if authentication was successful.
     * @throws ConnectionFailedException
     * @throws InvalidCredentialsException
     * @throws InternalServerErrorException
     */
    User AuthenticateUser(String username, String password) throws ConnectionFailedException,
            InvalidCredentialsException, InternalServerErrorException;

    /**
     * Retrieves a list of all of the friends that myUser is friends with.
     * @param myUser The user to retrieve a list of friends for.
     * @return A list of all of the friends that myUser is friends with.
     * @throws ConnectionFailedException
     * @throws InvalidCredentialsException
     * @throws InvalidUserException
     * @throws InternalServerErrorException
     */
    List<User> GetFriendsList(User myUser) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException;

    /**
     * Attempts to befriend a specified user.
     * @param myUser The user that is trying to befriend the other.
     * @param friendUsername The user to befriend.
     * @return Whether or not the friend was successfully befriended.
     * @throws ConnectionFailedException
     * @throws InvalidCredentialsException
     * @throws InvalidUserException
     * @throws InternalServerErrorException
     * @throws AlreadyFriendsException
     */
    boolean AddFriend(User myUser, String friendUsername) throws ConnectionFailedException,
            InvalidCredentialsException, InvalidUserException, InternalServerErrorException,
            AlreadyFriendsException;

    /**
     * Attempts to register interest in a product for the user.
     * @param myUser The user to register interest for.
     * @param productName The name of the product to register interest for.
     * @param maxPrice The maximum price of the product when matching sales.
     * @return Whether or not the interest was successfully registered for the product.
     * @throws UserNotAuthorizedException
     * @throws InternalServerErrorException
     * @throws InvalidProductNameException
     * @throws InvalidPriceException
     * @throws InvalidUserException
     * @throws RegisteredInterestAlreadyExistsException
     * @throws UnrecognizedResponseException
     */
    boolean AddInterest(User myUser, String productName, double maxPrice) throws
            UserNotAuthorizedException, InternalServerErrorException, InvalidProductNameException,
            InvalidPriceException, InvalidUserException, RegisteredInterestAlreadyExistsException,
            UnrecognizedResponseException ;
    }
