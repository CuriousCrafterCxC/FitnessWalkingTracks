package hk.edu.hkmu.contactinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private ListView listView; // ui component for displaying all contacts (x8 contacts)
    static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the listView ui component by using findViewById method
        listView = (ListView) findViewById(R.id.listview);

        // Get contact list with thread class
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread();
        jsonHandlerThread.start();

        try {
            jsonHandlerThread.join();

            // after retrieved the json contents and stored into the "contactList", i.e. contactList is ready for display
            // 1. setup ListView component to display the contact list
            // 2. implement the item click event handling

            // 1.
            // Create an adapter object that accommodates a data list of items to views that becomes children of an adapter view
            // i.e. the Adapter object acts as a bridge between an ListView and the contacts for that view
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    ContactInfo.contactList,  // "contactList" that stores all the retrieved contacts, defined in ContactInfo
                    R.layout.list_view_layout, // layout resource represent item layout design
                    new String[] { ContactInfo.TITLE, ContactInfo.DISTRICT, ContactInfo.ROUTE },  // represent the three data that display in an item
                    new int[] { R.id.title, R.id.district, R.id.route }  // represent where the item is displayed
            );
            // Associate the adapter with the ListView
            listView.setAdapter(adapter);

            // 2.
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> contact = ContactInfo.contactList.get(position);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);// dialog creation
//
//                            builder.setTitle(contact.get(ContactInfo.TITLE));
//                            builder.setMessage("Mobile: " + contact.get(ContactInfo.DISTRICT));
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();

                            // 在第一個Activity中創建一個Bundle對象並添加一些數據
                            Bundle bundle = new Bundle();
                            bundle.putString("title", contact.get(ContactInfo.TITLE));
                            bundle.putString("district", contact.get(ContactInfo.DISTRICT));
                            bundle.putString("map_image",contact.get(ContactInfo.MAP_URL));
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
}