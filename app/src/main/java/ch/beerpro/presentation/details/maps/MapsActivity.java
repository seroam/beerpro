package ch.beerpro.presentation.details.maps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ch.beerpro.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Intent intent = getIntent();
        Log.e(TAG, "latitude: " + intent.getDoubleExtra("latitude", Double.MAX_VALUE) + "longitude: " + intent.getDoubleExtra("longitude", Double.MAX_VALUE));

        LatLng place = new LatLng(intent.getDoubleExtra("latitude", 16.7714039), intent.getDoubleExtra("longitude", -3.0167342));
        googleMap.addMarker(new MarkerOptions().position(place)
                .title(intent.getStringExtra("name")));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
    }
}

