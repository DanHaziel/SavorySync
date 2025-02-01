package com.example.savorysync.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;

import com.example.savorysync.model.AppDatabase;

import com.example.savorysync.R;
import com.example.savorysync.model.entity.LoginEntity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignUp;
    private TextView tvLoginRedirect;
    private AppDatabase appDatabase; // Room database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        appDatabase = AppDatabase.getDatabaseFromJava(this);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        // Handle Sign Up button click
        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                LoginEntity user = new LoginEntity();
                user.setEmail(email);
                user.setPassword(password);

                // Save user to database
                appDatabase.loginDao().insertDetails(user);

                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                // Redirect to Login page
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle Login redirect click
        tvLoginRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
