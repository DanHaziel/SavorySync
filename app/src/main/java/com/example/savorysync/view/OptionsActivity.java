package com.example.savorysync.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.List;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getDatabaseFromJava(this);

        // Obtenez l'utilisateur connecté
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser();

        if (loggedInUser != null) {
            // Récupérer les recettes de l'utilisateur
            List<RecetteEntity> userRecipes = db.recetteDao().getRecipesByUserId(loggedInUser.getId());

            // Configurer l'adaptateur
            OptionsAdapter adapter = new OptionsAdapter(this, R.layout.item_option, userRecipes, recipe -> {
                // Démarrer MainActivity en transmettant l'ID de la recette sélectionnée
                Intent intent = new Intent(this, RecipeDetailActivity.class);
                intent.putExtra("RECIPE_ID", recipe.getId());
                startActivity(intent);
            });

            recyclerView.setAdapter(adapter);
        }
    }
}
