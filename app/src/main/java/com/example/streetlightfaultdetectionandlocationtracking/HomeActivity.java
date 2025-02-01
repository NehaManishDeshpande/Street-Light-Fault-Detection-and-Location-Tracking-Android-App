package com.example.streetlightfaultdetectionandlocationtracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Initialize buttons
        Button mapsButton = findViewById(R.id.button);
        Button graphButton = findViewById(R.id.button2);

        // Set click listeners
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MapsActivity
                startActivity(new Intent(HomeActivity.this, MapsActivity.class));
            }
        });

        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start GraphActivity
                startActivity(new Intent(HomeActivity.this, GraphActivity.class));
            }
        });
    }
}

