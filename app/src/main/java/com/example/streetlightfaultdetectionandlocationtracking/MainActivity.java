package com.example.streetlightfaultdetectionandlocationtracking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;


public class MainActivity extends AppCompatActivity {
    private EditText txtEmail, txtPwd;
    private Button btnLogin;
    private TextView lnkRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtEmail = findViewById(R.id.txtEmail);
        txtPwd = findViewById(R.id.txtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        lnkRegister = findViewById(R.id.lnkRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPwd.getText().toString().trim();

                if (validateLogin(email, password)) {
                    // Redirect to HomeActivity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Finish MainActivity so user can't go back to it
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lnkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateLogin(String email, String password) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String registeredEmail = prefs.getString("email", null);
        String registeredPassword = prefs.getString("password", null);

        return email.equals(registeredEmail) && password.equals(registeredPassword);
    }
}
