package shopping.ServerConnection;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import shopping.Model.Sale;

/**
 * New XML parser. Trying out separating the XML parsing and server connection bits. Cleaned up
 * the server connection pretty well, will most likely continue it for the next milestone.
 */
public class ServerXMLParser {
    /**
     * Parses the XML response for a list of sales and returns them in a nice list object.
     * @param SalesXMLResponse The XML string to parse sales from.
     * @return The list of sales parsed from the given XML.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws InternalServerErrorException
     * @throws UserNotAuthorizedException
     * @throws UnrecognizedResponseException
     */
    public static List<Sale> ParseSalesXML(String SalesXMLResponse)
            throws ParserConfigurationException, IOException, SAXException,
            InternalServerErrorException, UserNotAuthorizedException,
            UnrecognizedResponseException {
        // Create a new DomDocument out of the XML
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(SalesXMLResponse));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);

        // Try and get the status from the response
        NodeList statusList = doc.getElementsByTagName("status");
        if (statusList.getLength() < 1) {
            // Empty server response, return throw an InternalServerError exception
            throw new InternalServerErrorException();
        }
        Element statusElement = (Element) statusList.item(0);
        String status = ((CharacterData) statusElement.getFirstChild()).getData();

        if (status.equals("success")) {
            NodeList saleList = doc.getElementsByTagName("sale");
            List<Sale> toReturn = new ArrayList<>();
            for(int i = 0; i < saleList.getLength(); i++)
            {
                if (saleList.item(i) instanceof Element) {
                    String postingUser = "";
                    String productName = "";
                    double productPrice = 0.0;
                    double maxPrice = 0.0;
                    String productLocation = "";
                    NodeList saleNodes = saleList.item(i).getChildNodes();
                    for (int j = 0; j < saleNodes.getLength(); j++) {
                        if (saleNodes.item(j) instanceof Element) {
                            if (saleNodes.item(j).getNodeName().equals("postingUser")) {
                                postingUser = saleNodes.item(j).getLastChild().getTextContent().trim();
                            } else if (saleNodes.item(j).getNodeName().equals("productName")) {
                                productName = saleNodes.item(j).getLastChild().getTextContent().trim();
                            } else if (saleNodes.item(j).getNodeName().equals("productPrice")) {
                                productPrice = Double.parseDouble(saleNodes.item(j).getLastChild().getTextContent().trim());
                            } else if (saleNodes.item(j).getNodeName().equals("maxPrice")) {
                                maxPrice = Double.parseDouble(saleNodes.item(j).getLastChild().getTextContent().trim());
                            } else if (saleNodes.item(j).getNodeName().equals("location")) {
                                productLocation = saleNodes.item(j).getLastChild().getTextContent().trim();
                            }
                        }
                    }
                    toReturn.add(new Sale(postingUser, productName, productPrice, maxPrice, productLocation));
                }
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
    }

    public static String ParseFriendsListXML(String FriendXMLResponse) {
        int parenthesisPosition = FriendXMLResponse.indexOf("(");
        String name = FriendXMLResponse.substring(0, parenthesisPosition);
        String username = FriendXMLResponse.substring(parenthesisPosition + 1, FriendXMLResponse.length() - 1);
        String friendsListEntry = name + " --- " + username;
        return friendsListEntry;
    }
}
