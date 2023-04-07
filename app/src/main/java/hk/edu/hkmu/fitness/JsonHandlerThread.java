package hk.edu.hkmu.fitness;

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
    // URL to get tracks JSON file
    private static String jsonUrl = "https://raw.githubusercontent.com/Professionalthingsarecrap/Fitness-walking-tracks/main/facility-fw.json";

    // send request to the url, no need to be changed
    public String lang;
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
        // "trackStr" variable store the json file content
        String trackStr = makeRequest();
//        Log.e(TAG, "Response from url: " + trackStr);
//        Log.e(TAG, "Current Language is: " + lang);
        if (trackStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(trackStr);

                // Getting JSON Array node
                JSONArray tracks = jsonObj.getJSONArray("Items");
                // Empty the array to refresh the tracks
                FitnessTrack.emptyFitness();
                // looping through All tracks
                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject c = tracks.getJSONObject(i);
                    String title = c.getString("Title_" + lang);
                    String district = c.getString("District_" + lang);
                    String route = c.getString("Route_"+lang);
                    String regex = "[^0-9.]";
                    String Lati = c.getString("Latitude");
                    Lati = Lati.replaceAll(regex,"");
                    String Longi = c.getString("Longitude");
                    Longi = Longi.replaceAll(regex,"");
                    route = route.replace("<br>","\n"); //modify the route info for better expression
                    String howtoaccess = c.getString("HowToAccess_"+lang);
                    howtoaccess = howtoaccess.replace("<br>","\n");
                    String mapURL = c.getString("MapURL_"+lang);
//                    Log.e(TAG,"RAW: "+ Lati + " "+ Longi);
                    Double Latitude = Double.parseDouble(Lati);
                    Double Longitude = Double.parseDouble(Longi);
//                    Log.e(TAG,"Latitude: " + Latitude + "\n Longitude: " + Longitude);
                    FitnessTrack.addFitness (title, district, route, howtoaccess, mapURL, Latitude, Longitude);


                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}