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
 * Created by Zach on 4/1/2015.
 */
public class ServerXMLParser {
    public static List<Sale> ParseSalesXML(String SalesXMLResponse) throws Exception {
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

                /*
                NodeList saleInfo = saleList.item(i).getChildNodes();
                throw new Exception("Sale info length: " + saleInfo.getLength());// + ", Sale with info: " + saleInfo.item(0).getLastChild().getTextContent().trim() + ", " + saleInfo.item(1).getLastChild().getTextContent().trim() + " , " + saleInfo.item(2).getLastChild().getTextContent().trim() + " , " + saleInfo.item(3).getLastChild().getTextContent().trim() + " , " + saleInfo.item(4).getLastChild().getTextContent().trim());
                // toReturn.add(new Sale(saleInfo.item(0).getTextContent(), saleInfo.item(1).getTextContent(), Double.parseDouble(saleInfo.item(2).getTextContent()), Double.parseDouble(saleInfo.item(3).getTextContent()), saleInfo.item(4).getTextContent()));
                */
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
}
