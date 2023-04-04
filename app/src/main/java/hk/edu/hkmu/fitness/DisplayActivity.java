package hk.edu.hkmu.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    public TextView titleView;
    public TextView districtView;
    public TextView routeView;
    public TextView htaView;
    public Button webButton;
    public ImageView URL_map_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        titleView = findViewById(R.id.displayTitle);
        districtView = findViewById(R.id.displayDistrict);
        routeView = findViewById(R.id.displayRoute);
        htaView = findViewById(R.id.howtoaccess);
        URL_map_image = findViewById(R.id.url_map_picture);
        webButton = findViewById(R.id.GPSLocation);

        // 從Intent對象中獲取Bundle對象
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String district = bundle.getString("district");
        String howtoaccess = bundle.getString("howtoaccess");
        howtoaccess = "How to Acess: \n" + howtoaccess;
        String route = bundle.getString("route");
        String Map_image_url = bundle.getString("map_image");

        Picasso.get().load(Map_image_url).into(URL_map_image);

        titleView.setText(title);
        districtView.setText(district);
        routeView.setText(route);
        htaView.setText(howtoaccess);

    }


}