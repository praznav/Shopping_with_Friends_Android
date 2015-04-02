package shopping;

import junit.framework.TestCase;

import java.util.List;

import shopping.Model.Sale;
import shopping.ServerConnection.AgaviServerConnection;
import shopping.ServerConnection.InternalServerErrorException;
import shopping.ServerConnection.ServerConnection;
import shopping.ServerConnection.ServerXMLParser;
import shopping.ServerConnection.UnrecognizedResponseException;
import shopping.ServerConnection.UserNotAuthorizedException;
import java.util.ArrayList;

/**
 * Created by Zach on 4/1/2015.
 */
public class GetSalesTest extends TestCase {
    public void testInvalidResponse() {
        try {
            ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fail("Parsing sales XML did not throw an exception when testing invalid xml");
        } catch (InternalServerErrorException e) {

        } catch (Exception e) {
            fail("Parsing sales XML returned a different exception than was expected");
            fail("Exception thrown has message: " + e.getMessage());
        }
    }

    public void testNotAuthorized() {
        try {
            ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<response>\n" +
                    "    <status>notAuthorized</status>\n" +
                    "    <message>You are not authorized to perform this action. Please check your username and password, then try again.</message>\n" +
                    "</response>");
            fail("Parsing sales XML did not throw an exception when testing invalid xml");
        } catch (UserNotAuthorizedException e) {

        } catch (Exception e) {
            fail("Parsing sales XML returned a different exception than was expected");
            fail("Exception thrown has message: " + e.getMessage());
        }
    }

    public void testInternalServerError() {
        try {
            ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<response>\n" +
                    "    <status>error</status>\n" +
                    "    <message>An internal server error occurred. Please try again later</message>\n" +
                    "</response>");
            fail("Parsing sales XML did not throw an exception when testing invalid xml");
        } catch (InternalServerErrorException e) {

        } catch (Exception e) {
            fail("Parsing sales XML returned a different exception than was expected");
            fail("Exception thrown has message: " + e.getMessage());
        }
    }

    public void testUnrecognizedResponse() {
        try {
            ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<response>\n" +
                    "    <status>Ahh!</status>\n" +
                    "    <message>An unexpected, but inevitable, betrayal!</message>\n" +
                    "</response>");
            fail("Parsing sales XML did not throw an exception when testing invalid xml");
        } catch (UnrecognizedResponseException e) {

        } catch (Exception e) {
            fail("Parsing sales XML returned a different exception than was expected");
            fail("Exception thrown has message: " + e.getMessage());
        }
    }

    public void testEmptyList() {
        List<Sale> expectedOutput = new ArrayList<>();
        List<Sale> actualOutput = null;
        try {
            actualOutput = ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<response>\n" +
                    "    <status>success</status>\n" +
                    "    <sales />\n" +
                    "</response>");
        } catch (Exception e) {
            fail("Parsing sales XML threw an exception with message: " + e.getMessage());
        }
        assertEquals("List of sales did not match expected result", expectedOutput, actualOutput);
    }

    public void testPopulatedList() {
        List<Sale> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Sale("postingUserA", "testingProductA", 100.00, 300.00, "testingLocationA"));
        expectedOutput.add(new Sale("postingUserB", "testingProductB", 50.00, 150.00, "testingLocationB"));
        List<Sale> actualOutput = null;
        try {
            actualOutput = ServerXMLParser.ParseSalesXML("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<response>\n" +
                    "    <status>success</status>\n" +
                    "    <sales>\n" +
                    "        <sale>\n" +
                    "            <postingUser>postingUserA</postingUser>\n" +
                    "            <productName>testingProductA</productName>\n" +
                    "            <productPrice>100.00</productPrice>\n" +
                    "            <maxPrice>300.00</maxPrice>\n" +
                    "            <location>testingLocationA</location>\n" +
                    "        </sale>\n" +
                    "        <sale>\n" +
                    "            <postingUser>postingUserB</postingUser>\n" +
                    "            <productName>testingProductB</productName>\n" +
                    "            <productPrice>50.00</productPrice>\n" +
                    "            <maxPrice>150.00</maxPrice>\n" +
                    "            <location>testingLocationB</location>\n" +
                    "        </sale>\n" +
                    "    </sales>\n" +
                    "</response>");
        } catch (Exception e) {
            fail("Parsing sales XML threw an exception with message: " + e.getMessage());
        }
        assertTrue("List of sales did not match expected result", CheckSalesListEquality(expectedOutput, actualOutput));
    }

    private boolean CheckSalesListEquality(List<Sale> listA, List<Sale> listB) {
        if (listA.size() != listB.size()) {
            return false;
        }
        for (int i = 0; i < listA.size(); i++) {
            if (!listA.get(i).getUser().equals(listB.get(i).getUser())) {
                return false;
            } else if (!listA.get(i).getProduct().equals(listB.get(i).getProduct())) {
                return false;
            } else if (listA.get(i).getPriceFound() != listB.get(i).getPriceFound()) {
                return false;
            } else if (listA.get(i).getPriceRequested() != listB.get(i).getPriceRequested()) {
                return false;
            } else if (!listA.get(i).getLocation().equals(listB.get(i).getLocation())) {
                return false;
            }
        }
        return true;
    }
}
