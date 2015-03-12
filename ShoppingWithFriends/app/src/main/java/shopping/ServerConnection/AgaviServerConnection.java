package shopping.ServerConnection;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import shopping.Model.Sale;
import shopping.Model.User;

/**
 * Agavi based implementation of the ServerConnection. Makes all requests to the teamkevin.me Agavi
 * based server.
 *
 * @author Zachary Peterson
 */
public class AgaviServerConnection implements ServerConnection {
    private String mConnectionUrl = "http://teamkevin.me";
    private String mUsersUrl = "/Users";
    private String mRegisterUrl = "/Register";
    private String mSalesUrl = "/Sales";
    private String mRegisterInterestUrl = "/RegisterInterest";
    private String mPostSaleUrl = "/RegisterSale";
    private String mGetSaleUrl = "/Get";

    private static AgaviServerConnection mInstance;

    private AgaviServerConnection() {
    }

    /**
     * Gets the instance of this AgaviServerConnection.
     * @return The singleton instance of this AgaviServerConnection.
     */
    public static AgaviServerConnection GetInstance() {
        if (mInstance == null) {
            mInstance = new AgaviServerConnection();
        }
        return mInstance;
    }

    @Override
    public boolean RegisterUser(User newUser) throws ConnectionFailedException,
            UsernameTakenException, InvalidDataException, InternalServerErrorException,
            UnrecognizedResponseException {
        // Set up the HTTP post request
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(mConnectionUrl + mUsersUrl + mRegisterUrl);

        // Add the post parameters to the request
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(5);
        parameters.add(new BasicNameValuePair("username", newUser.getUsername()));
        parameters.add(new BasicNameValuePair("password", newUser.getPassword()));
        parameters.add(new BasicNameValuePair("email", newUser.getEmailAddress()));
        parameters.add(new BasicNameValuePair("firstName", newUser.getFirstName()));
        parameters.add(new BasicNameValuePair("lastName", newUser.getLastName()));
        try {
            post.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error encoding POST parameters");
            System.out.println(e.getMessage());
        }


        HttpResponse response;
        HttpEntity entity = null;
        try {
            // Execute the POST request and save the response
            response = client.execute(post);

            // Read the full response and create a string out of it that can be parsed with
            // an XML parser
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            try {
                // Create a new DomDocument out of the XML
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(sb.toString()));
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                // Try and get the status from the response
                NodeList statusList = doc.getElementsByTagName("status");
                if (statusList.getLength() < 1) {
                    return false;
                }
                Element statusElement = (Element) statusList.item(0);
                String status = ((CharacterData) statusElement.getFirstChild()).getData();

                if (status.equals("success")) {
                    // If the status was success, registration was too
                    return true;
                } else if (status.equals("taken")) {
                    // Username already taken, set the username error appropriately and show a
                    // toast to let the user know
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String usernameError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new UsernameTakenException(usernameError);
                } else if (status.equals("error")) {
                    // Server error occurred, show a toast with the contents of the message
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InternalServerErrorException(serverError);
                } else if (status.equals("invalid")) {
                    // One of the fields the user entered was invalid, go through each one
                    // and check for validation errors
                    InvalidDataException invalidException = new InvalidDataException();
                    NodeList usernameErrors = doc.getElementsByTagName("usernameErrors");
                    if (usernameErrors.getLength() > 0) {
                        // There were username errors
                        NodeList usernameChildren = usernameErrors.item(0).getChildNodes();
                        for (int i = 0; i < usernameChildren.getLength(); i++) {
                            if (usernameChildren.item(i) instanceof Element) {
                                String usernameError = usernameChildren.item(i).getLastChild().getTextContent().trim();
                                invalidException.AddInvalidField("username", usernameError);
                            }
                        }
                    }

                    NodeList emailErrors = doc.getElementsByTagName("emailErrors");
                    if (emailErrors.getLength() > 0) {
                        // There were email errors
                        NodeList emailChildren = emailErrors.item(0).getChildNodes();
                        for (int i = 0; i < emailChildren.getLength(); i++) {
                            if (emailChildren.item(i) instanceof Element) {
                                String emailError = emailChildren.item(i).getLastChild().getTextContent().trim();
                                invalidException.AddInvalidField("email", emailError);
                            }
                        }
                    }

                    NodeList passwordErrors = doc.getElementsByTagName("passwordErrors");
                    if (passwordErrors.getLength() > 0) {
                        // There were password errors
                        NodeList passwordChildren = passwordErrors.item(0).getChildNodes();
                        for (int i = 0; i < passwordChildren.getLength(); i++) {
                            if (passwordChildren.item(i) instanceof Element) {
                                String passwordError = passwordChildren.item(i).getLastChild().getTextContent().trim();
                                invalidException.AddInvalidField("password", passwordError);
                            }
                        }
                    }

                    NodeList firstNameErrors = doc.getElementsByTagName("firstNameErrors");
                    if (firstNameErrors.getLength() > 0) {
                        // There were first name errors
                        NodeList firstNameChildren = firstNameErrors.item(0).getChildNodes();
                        for (int i = 0; i < firstNameChildren.getLength(); i++) {
                            if (firstNameChildren.item(i) instanceof Element) {
                                String firstNameError = firstNameChildren.item(i).getLastChild().getTextContent().trim();
                                invalidException.AddInvalidField("firstName", firstNameError);
                            }
                        }
                    }

                    NodeList lastNameErrors = doc.getElementsByTagName("lastNameErrors");
                    if (lastNameErrors.getLength() > 0) {
                        // There were last name errors
                        NodeList lastNameChildren = lastNameErrors.item(0).getChildNodes();
                        for (int i = 0; i < lastNameChildren.getLength(); i++) {
                            if (lastNameChildren.item(i) instanceof Element) {
                                String lastNameError = lastNameChildren.item(i).getLastChild().getTextContent().trim();
                                invalidException.AddInvalidField("lastName", lastNameError);
                            }
                        }
                    }
                    throw invalidException;
                } else {
                    // The server response was unrecognized
                    throw new UnrecognizedResponseException();
                }
            } catch (ParserConfigurationException e) {
                Log.e("XML Exception", "Caught a parser configuration exception while creating the document builder", e);
            } catch (SAXException e) {
                Log.e("XML Exception", "Caught a SAX exception while parsing the XML response", e);
            }
            return false;
        } catch (IOException e){
            System.out.println("IO Exception");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public User AuthenticateUser(String username, String password) throws ConnectionFailedException, InvalidCredentialsException, InternalServerErrorException {
        return null;
    }

    @Override
    public List<User> GetFriendsList(User myUser) throws ConnectionFailedException, InvalidCredentialsException, InvalidUserException, InternalServerErrorException {
        return null;
    }

    @Override
    public boolean AddFriend(User myUser, String friendUsername) throws ConnectionFailedException, InvalidCredentialsException, InvalidUserException, InternalServerErrorException, AlreadyFriendsException {
        return false;
    }

    @Override
    public boolean AddInterest(User myUser, String productName, double maxPrice) throws UserNotAuthorizedException, InternalServerErrorException, InvalidProductNameException, InvalidPriceException, InvalidUserException, RegisteredItemAlreadyExistsException, UnrecognizedResponseException {
        HttpClient client;
        HttpPost post;
        ArrayList<NameValuePair> postParameters;
        client = new DefaultHttpClient();
        post = new HttpPost(mConnectionUrl + mSalesUrl + mRegisterInterestUrl);

        String username = myUser.getUsername();
        String password = myUser.getPassword();
        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username));
        postParameters.add(new BasicNameValuePair("password", password));
        postParameters.add(new BasicNameValuePair("productName", productName));
        postParameters.add(new BasicNameValuePair("maxPrice","" + maxPrice));
        try {
            post.setEntity(new UrlEncodedFormEntity(postParameters));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error encoding POST parameters");
            System.out.println(e.getMessage());
        }


        HttpResponse response;
        HttpEntity entity = null;
        try {
            // Execute the POST request and save the response
            response = client.execute(post);

            // Read the full response and create a string out of it that can be parsed with
            // an XML parser
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            try {
                // Create a new DomDocument out of the XML
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(sb.toString()));
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                // Try and get the status from the response
                NodeList statusList = doc.getElementsByTagName("status");
                if (statusList.getLength() < 1) {
                    return false;
                }
                Element statusElement = (Element) statusList.item(0);
                String status = ((CharacterData) statusElement.getFirstChild()).getData();

                if (status.equals("success")) {
                    // If the status was success, register interest succeeded
                    return true;
                } else if (status.equals("notAuthorized")) {
                    // User not authorized
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String authError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new UserNotAuthorizedException(authError);
                } else if (status.equals("error")) {
                    // Server error occurred, show a toast with the contents of the message
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InternalServerErrorException(serverError);
                } else if (status.equals("invalidProductName")) {
                    // Product name was invalid
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidProductError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidProductNameException(invalidProductError);
                } else if (status.equals("invalidPrice")) {
                    // Product price was invalid
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidPriceError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidPriceException(invalidPriceError);
                } else if (status.equals("invalidUser")) {
                    // User somehow became invalid, possibly due to deletion from database, etc.
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidUserError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidUserException(invalidUserError);
                } else if (status.equals("alreadyExists")) {
                    // The attempted item is already in the database
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String alreadyExistsError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new RegisteredItemAlreadyExistsException(alreadyExistsError);
                } else {
                    // The server response was unrecognized
                    throw new UnrecognizedResponseException();
                }
            } catch (ParserConfigurationException e) {
                Log.e("XML Exception", "Caught a parser configuration exception while creating the document builder", e);
            } catch (SAXException e) {
                Log.e("XML Exception", "Caught a SAX exception while parsing the XML response", e);
            }
            return false;
        } catch (IOException e){
            System.out.println("IO Exception");
            System.out.println(e.getMessage());
        }
        return false;

    }

    public boolean PostSale(User myUser, String productName, double price, String location) throws UserNotAuthorizedException, InternalServerErrorException, InvalidProductNameException, InvalidPriceException, InvalidUserException, UnrecognizedResponseException, RegisteredItemAlreadyExistsException
        {
        HttpClient client;
        HttpPost post;
        ArrayList<NameValuePair> postParameters;
        client = new DefaultHttpClient();
        post = new HttpPost(mConnectionUrl + mSalesUrl + mPostSaleUrl);

        String username = myUser.getUsername();
        String password = myUser.getPassword();
        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username));
        postParameters.add(new BasicNameValuePair("password", password));
        postParameters.add(new BasicNameValuePair("productName", productName));
        postParameters.add(new BasicNameValuePair("productLocation", location));
        postParameters.add(new BasicNameValuePair("productPrice","" + price));
        try {
            post.setEntity(new UrlEncodedFormEntity(postParameters));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error encoding POST parameters");
            System.out.println(e.getMessage());
        }


        HttpResponse response;
        HttpEntity entity = null;
        try {
            // Execute the POST request and save the response
            response = client.execute(post);

            // Read the full response and create a string out of it that can be parsed with
            // an XML parser
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            try {
                // Create a new DomDocument out of the XML
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(sb.toString()));
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                // Try and get the status from the response
                NodeList statusList = doc.getElementsByTagName("status");
                if (statusList.getLength() < 1) {
                    return false;
                }
                Element statusElement = (Element) statusList.item(0);
                String status = ((CharacterData) statusElement.getFirstChild()).getData();

                if (status.equals("success")) {
                    // If the status was success, register interest succeeded
                    return true;
                } else if (status.equals("notAuthorized")) {
                    // User not authorized
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String authError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new UserNotAuthorizedException(authError);
                } else if (status.equals("error")) {
                    // Server error occurred, show a toast with the contents of the message
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InternalServerErrorException(serverError);
                } else if (status.equals("invalidProductName")) {
                    // Product name was invalid
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidProductError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidProductNameException(invalidProductError);
                } else if (status.equals("invalidPrice")) {
                    // Product price was invalid
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidPriceError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidPriceException(invalidPriceError);
                } else if (status.equals("invalidUser")) {
                    // User somehow became invalid, possibly due to deletion from database, etc.
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String invalidUserError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InvalidUserException(invalidUserError);
                } else if (status.equals("alreadyExists")) {
                    // The attempted item is already in the database
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String alreadyExistsError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new RegisteredItemAlreadyExistsException(alreadyExistsError);
                } else
                {
                    // The server response was unrecognized
                    throw new UnrecognizedResponseException();
                }
            } catch (ParserConfigurationException e) {
                Log.e("XML Exception", "Caught a parser configuration exception while creating the document builder", e);
            } catch (SAXException e) {
                Log.e("XML Exception", "Caught a SAX exception while parsing the XML response", e);
            }
            return false;
        } catch (IOException e){
            System.out.println("IO Exception");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Sale> GetSales(User myUser) throws UserNotAuthorizedException, InternalServerErrorException, UnrecognizedResponseException
    {
        HttpClient client;
        HttpGet get;
        ArrayList<NameValuePair> postParameters;
        client = new DefaultHttpClient();
        String username = myUser.getUsername();
        String password = myUser.getPassword();
        get = new HttpGet(mConnectionUrl + mSalesUrl + mGetSaleUrl + "?username=" + username + "&password=" + password);

        HttpResponse response;
        HttpEntity entity = null;
        try {
            // Execute the POST request and save the response
            response = client.execute(get);

            // Read the full response and create a string out of it that can be parsed with
            // an XML parser
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            try {
                // Create a new DomDocument out of the XML
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(sb.toString()));
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                // Try and get the status from the response
                NodeList statusList = doc.getElementsByTagName("status");
                if (statusList.getLength() < 1) {
                    return null;
                }
                Element statusElement = (Element) statusList.item(0);
                String status = ((CharacterData) statusElement.getFirstChild()).getData();

                if (status.equals("success")) {
                    NodeList saleList = doc.getElementsByTagName("sale");
                    List<Sale> toReturn = new ArrayList<Sale>();
                    Log.d("Test", "" + saleList.getLength());
                    for(int i = 0; i < saleList.getLength(); i++)
                    {
                        Log.d("Test", "Adding " + i);
                        NodeList saleInfo = saleList.item(i).getChildNodes();
                        toReturn.add(new Sale(saleInfo.item(0).getTextContent(), saleInfo.item(1).getTextContent(), Double.parseDouble(saleInfo.item(2).getTextContent()), Double.parseDouble(saleInfo.item(3).getTextContent()), saleInfo.item(4).getTextContent()));
                    }
                    return toReturn;
                } else if (status.equals("notAuthorized")) {
                    // User not authorized
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String authError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new UserNotAuthorizedException(authError);
                } else if (status.equals("error")) {
                    // Server error occurred, show a toast with the contents of the message
                    Element messageElement = (Element) doc.getElementsByTagName("message").item(0);
                    final String serverError = ((CharacterData) messageElement.getFirstChild()).getData();
                    throw new InternalServerErrorException(serverError);
                } else {
                    // The server response was unrecognized
                    throw new UnrecognizedResponseException();
                }
            } catch (ParserConfigurationException e) {
                Log.e("XML Exception", "Caught a parser configuration exception while creating the document builder", e);
            } catch (SAXException e) {
                Log.e("XML Exception", "Caught a SAX exception while parsing the XML response", e);
            }
        } catch (IOException e){
            System.out.println("IO Exception");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
