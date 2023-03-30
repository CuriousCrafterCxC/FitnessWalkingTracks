package hk.edu.hkmu.contactinfo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonHandlerThread extends Thread {
    private static final String TAG = "JsonHandlerThread";
    // URL to get contacts JSON file
    private static String jsonUrl = "https://raw.githubusercontent.com/Professionalthingsarecrap/Fitness-walking-tracks/main/facility-fw.json";

    // send request to the url, no need to be changed
    public static String makeRequest() {
        String response = null;
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = inputStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    // download of JSON file from the url to the app
    private static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        }
        return sb.toString();
    }

    public void run() {
        // "contactStr" variable store the json file content
        String contactStr = makeRequest();
        Log.e(TAG, "Response from url: " + contactStr);

        if (contactStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(contactStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("Items");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String title = c.getString("Title_en");
                    String district = c.getString("District_en");
                    String route = c.getString("Route_en");
                    String howtoaccess = c.getString("HowToAccess_en");
                    String mapURL = c.getString("MapURL_en");
//                    Double Latitude = c.getDouble("Latitude");
//                    Double Longitude = c.getDouble("Longitude");
                    ContactInfo.addContact(title, district, route, howtoaccess,mapURL);


//                    String id = c.getString("id");
//                    String name = c.getString("name");
//                    String email = c.getString("email");
//                    String address = c.getString("address");
//                    String gender = c.getString("gender");
//
//                    // Phonee node is JSON Object
//                    JSONObject phone = c.getJSONObject("phone");
//                    String mobile = phone.getString("mobile");
//                    String home = phone.getString("home");
//                    String office = phone.getString("office");
//
//
//                    // Add contact (name, email, address) to contact list
//                    ContactInfo.addContact(name, email, address, mobile);

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}