package hk.edu.hkmu.contactinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class DisplayActivity extends AppCompatActivity {

    public TextView titleView;
    public TextView districtView;
    private Button webButton;
    public ImageView URL_map_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        titleView = findViewById(R.id.displayTitle);
        districtView = findViewById(R.id.dispalyDistrict);
        URL_map_image = findViewById(R.id.url_map_picture);
        webButton = findViewById(R.id.GPSLocation);

        // 從Intent對象中獲取Bundle對象
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String district = bundle.getString("district");
        String Map_image_url = bundle.getString("map_image");

        Picasso.get().load(Map_image_url).into(URL_map_image);

        titleView.setText(title);
        districtView.setText(district);

    }


}