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
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.view.MainActivity;

import com.example.savorysync.R;
import com.example.savorysync.view.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appDatabase = AppDatabase.getDatabaseFromJava(this);

        // Initialize views
        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Redirect to SignUpActivity
        TextView tvSignUpRedirect = findViewById(R.id.tvSignUpRedirect);
        tvSignUpRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle login button click
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Check login credentials
                LoginEntity user = appDatabase.loginDao().login(email, password);
                if (user != null) {
                    // Set all other users to logged out
                    appDatabase.loginDao().logoutAllUsers();

                    // Set the current user to logged in
                    appDatabase.loginDao().loginUser(email);

                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Redirect to the main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
