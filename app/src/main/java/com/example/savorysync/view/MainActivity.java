package com.example.savorysync.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.entity.LoginEntity;

public class MainActivity extends AppCompatActivity {

    private Button btnRecipe, btnShoppingList, btnAddRecipe;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getDatabaseFromJava(this);
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser();

        if (loggedInUser != null) {
            Toast.makeText(this, "Utilisateur connecté : " + loggedInUser.getId(), Toast.LENGTH_SHORT).show();
        }


        // Initialize buttons
        btnRecipe = findViewById(R.id.btnRecipe);
        btnShoppingList = findViewById(R.id.btnShoppingList);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);

        // Set up listeners for navigation
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecipeDetailActivity
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ShoppingListActivity
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                startActivity(intent);
            }
        });

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ShoppingListActivity
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}
