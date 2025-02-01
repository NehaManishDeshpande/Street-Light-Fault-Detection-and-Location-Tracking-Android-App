package com.example.streetlightfaultdetectionandlocationtracking;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    private DatabaseReference databaseReference;
    private float sunIntensity, bulb1Intensity, bulb2Intensity;
    private float bulb1Power, bulb2Power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FirebaseApp.initializeApp(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Map Fragment is null!");
        }

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Street");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set custom InfoWindowAdapter
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Fetch and display Firebase data
        fetchFirebaseData();
    }

    private void fetchFirebaseData() {
        DatabaseReference sunRef = databaseReference.child("sun").child("lightIntensity0");
        DatabaseReference bulb1PowerRef = databaseReference.child("bulb1").child("power1");
        DatabaseReference bulb1IntensityRef = databaseReference.child("bulb1").child("lightIntensity1");
        DatabaseReference bulb2PowerRef = databaseReference.child("bulb2").child("power2");
        DatabaseReference bulb2IntensityRef = databaseReference.child("bulb2").child("lightIntensity2");

        sunRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sunIntensity = snapshot.getValue(Float.class);
                updateMapWithFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching sun intensity data", error.toException());
            }
        });

        bulb1PowerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bulb1Power = snapshot.getValue(Float.class);
                updateMapWithFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching bulb1 power data", error.toException());
            }
        });

        bulb1IntensityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bulb1Intensity = snapshot.getValue(Float.class);
                updateMapWithFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching bulb1 intensity data", error.toException());
            }
        });

        bulb2PowerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bulb2Power = snapshot.getValue(Float.class);
                updateMapWithFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching bulb2 power data", error.toException());
            }
        });

        bulb2IntensityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bulb2Intensity = snapshot.getValue(Float.class);
                updateMapWithFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching bulb2 intensity data", error.toException());
            }
        });
    }

    private void updateMapWithFirebaseData() {
        LatLng bulb1Location = new LatLng(18.463769335936536, 73.86818781484878);
        LatLng bulb2Location = new LatLng(18.464769335936536, 73.86918781484878);

        mMap.clear();

        // Add markers for Bulb 1
        mMap.addMarker(new MarkerOptions()
                .position(bulb1Location)
                .title("Bulb 1")
                .snippet(getBulbDescription(bulb1Intensity, bulb1Power))
                .icon(BitmapDescriptorFactory.defaultMarker(getColorForStatus(bulb1Intensity, sunIntensity))));

        // Add markers for Bulb 2
        mMap.addMarker(new MarkerOptions()
                .position(bulb2Location)
                .title("Bulb 2")
                .snippet(getBulbDescription(bulb2Intensity, bulb2Power))
                .icon(BitmapDescriptorFactory.defaultMarker(getColorForStatus(bulb2Intensity, sunIntensity))));

        // Move camera to a suitable zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bulb1Location, 15));

        // Log data
        Log.d(TAG, "Bulb 1 - Power: " + bulb1Power + " Watt, Intensity: " + bulb1Intensity);
        Log.d(TAG, "Bulb 2 - Power: " + bulb2Power + " Watt, Intensity: " + bulb2Intensity);
        Log.d(TAG, "Sun Intensity: " + sunIntensity);
    }

    private float getColorForStatus(float intensity, float sunIntensity) {
        if (sunIntensity > 50) {
            return BitmapDescriptorFactory.HUE_YELLOW; // Day Time
        } else if (intensity > 100) {
            return BitmapDescriptorFactory.HUE_GREEN; // Working
        } else {
            return BitmapDescriptorFactory.HUE_RED; // Faulty
        }
    }

    private String getBulbDescription(float intensity, float power) {
        if (sunIntensity > 50) {
            return "Status: Day Time";
        } else if (intensity > 100) {
            return "Status: Working\nPower: " + power + " Watt";
        } else {
            return "Status: Not Working";
        }
    }

    // Custom InfoWindowAdapter class
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mWindow;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        private void render(Marker marker, View view) {
            String title = marker.getTitle();
            TextView titleUi = view.findViewById(R.id.title);
            if (title != null) {
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = view.findViewById(R.id.snippet);
            if (snippet != null) {
                snippetUi.setText(snippet);
            } else {
                snippetUi.setText("");
            }
        }
    }
}
