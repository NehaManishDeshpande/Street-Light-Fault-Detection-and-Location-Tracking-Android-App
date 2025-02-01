package com.example.streetlightfaultdetectionandlocationtracking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText txtName, txtEmail, txtPwd;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPwd = findViewById(R.id.txtPwd);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPwd.getText().toString().trim();

                // Save user credentials
                SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                // Redirect to MainActivity
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Finish RegistrationActivity so user can't go back to it
            }
        });

        // Handle click on "Already Registered? Login here"
        TextView lnkLogin = findViewById(R.id.lnkLogin);
        lnkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainActivity
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Optionally finish RegistrationActivity
            }
        });
    }
}
