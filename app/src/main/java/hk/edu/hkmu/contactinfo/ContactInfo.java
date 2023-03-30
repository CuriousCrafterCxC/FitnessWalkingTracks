package hk.edu.hkmu.contactinfo;

import java.util.ArrayList;
import java.util.HashMap;

// A utility class for creating contact list
public class ContactInfo {
    public static String TITLE = "title"; // used in "new String []..." in MainActivity program
    public static String DISTRICT = "district"; // used in "new String []..." in MainActivity program
    public static String ROUTE = "route"; // used in "new String []..." in MainActivity program
    public static String HOW_TO_ACCESS = "HowToAccess"; // used in "new String []..." in MainActivity program
    public static String MAP_URL = "MapURL";

    // "contactList" variable used for storing all contact that retrieved from JSON
    // it is used in JsonHandlerThread and also MainActivity program
    public static ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

    // addContact is a function
    // Creates and add contact to contact list
    // x4 input, representing name, email, address and mobile
    public static void addContact(String title, String district, String route, String HowToAccess, String MapURL) {
        // Create contact
        HashMap<String, String> contact = new HashMap<>();
        contact.put(TITLE, title);
        contact.put(DISTRICT, district);
        contact.put(ROUTE, route);
        contact.put(HOW_TO_ACCESS, HowToAccess);
        contact.put(MAP_URL, MapURL);

        // Add contact to contact list
        contactList.add(contact);
    }
}
