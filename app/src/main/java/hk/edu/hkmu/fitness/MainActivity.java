package hk.edu.hkmu.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private ListView listView; // ui component for displaying all fitness tracks
    static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the listView ui component by using findViewById method
        listView = (ListView) findViewById(R.id.listview);

        // Get fitness tracks list with thread class
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread();
        jsonHandlerThread.lang = Settings.getLang();
        jsonHandlerThread.start();

        try {
            jsonHandlerThread.join();

            // after retrieved the json contents and stored into the "fitnessList"
            // 1. setup ListView component to display the fitness tracks list
            // 2. implement the item click event handling

            // 1.
            // Create an adapter object that accommodates a data list of items to views that becomes children of an adapter view
            // i.e. the Adapter object acts as a bridge between an ListView and the tracks for that view
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    FitnessTrack.fitnessList,  // "fitnessList" that stores all the fitness tracks' info
                    R.layout.list_view_layout, // layout resource represent item layout design
                    new String[]{FitnessTrack.TITLE, FitnessTrack.DISTRICT, FitnessTrack.ROUTE},  // represent the three data that display in an item
                    new int[]{R.id.title, R.id.district, R.id.route}  // represent where the item is displayed
            );
            // Associate the adapter with the ListView
            listView.setAdapter(adapter);

            // 2.
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> fitness = FitnessTrack.fitnessList.get(position);
                            HashMap<String, Double> fitnessMap = FitnessTrack.fitnessListMap.get(position);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);// dialog creation
//
//                            builder.setTitle(fitness.get(FitnessTrack.TITLE));
//                            builder.setMessage("Mobile: " + fitness.get(FitnessTrack.DISTRICT));
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();

                            // 在第一個Activity中創建一個Bundle對象並添加一些數據
                            Bundle bundle = new Bundle();
                            bundle.putString("title", fitness.get(FitnessTrack.TITLE));
                            bundle.putString("district", fitness.get(FitnessTrack.DISTRICT));
                            bundle.putString("map_image", fitness.get(FitnessTrack.MAP_URL));
                            bundle.putString("howtoaccess", fitness.get(FitnessTrack.HOW_TO_ACCESS));
                            bundle.putString("route", fitness.get(FitnessTrack.ROUTE));
                            bundle.putDouble("latitude", fitnessMap.get(FitnessTrack.LATITUDE));
                            bundle.putDouble("longitude", fitnessMap.get(FitnessTrack.LONGITUDE));
                            // 創建一個Intent對象，指定要啟動的Activity
                            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                            // 將Bundle對象添加到Intent對象中
                            intent.putExtras(bundle);
                            //intent.putExtra(EXTRA_MESSAGE, urlStr);
                            startActivity(intent);

                        }
                    }
            );

        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}