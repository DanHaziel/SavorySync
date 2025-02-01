package com.example.savorysync.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.dao.RecetteDao;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;
import com.example.savorysync.viewmodel.RecetteViewModel;

import com.example.savorysync.R;

import java.util.ArrayList;
import java.util.List;



public class RecipeDetailActivity extends AppCompatActivity {

    RecetteViewModel recetteViewModel;
    RecetteDao recetteDao;
    private IngredientDao ingredientDao;
    private ListeDeCoursesDao listesDeCoursesDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        if (recipeId != -1) {
            // Charger ou afficher les détails de la recette
            Toast.makeText(this, "Recette sélectionnée : " + recipeId, Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getDatabaseFromJava(this.getApplicationContext());
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser();
        recetteDao = db.recetteDao();
        ingredientDao = db.ingredientDao();
        listesDeCoursesDao = db.listeDeCoursesDao();

        //List<RecetteEntity> recetteEntity = (List<RecetteEntity>) db.recetteDao().getAllRecipes();

        // Adapter
        RecetteAdapter adapter = new RecetteAdapter(this, R.layout.activity_recipe_detail, new ArrayList());
        recyclerView.setAdapter(adapter);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(new ArrayList<>());


        recetteViewModel = new ViewModelProvider(this).get(RecetteViewModel.class);
        //recetteViewModel.getAllRecipesForUser().observe(this, adapter::submitList);
        //recetteDao.getRecipeById(recipeId).observe(this, adapter::submitList);

        recetteDao.getRecipeById(recipeId).observe(this, recette -> {
            if (recette != null) {
                Log.d("Recettes", recette.toString());
                //List<RecetteEntity> recetteList = new ArrayList<>();
                //recetteList.add((RecetteEntity) recette);
                adapter.submitList(recette);

                // Charger les ingrédients de la recette
                //ingredientDao.getAllByRecipeId(recipeId).observe(this, ingredientAdapter::submitList);
                ingredientDao.getAllByRecipeId(recipeId).observe(this, ingredients -> {
                    Log.d("Ingredients", "Retrieved ingredients: " + ingredients);
                    ingredientAdapter.submitList(ingredients);
                });
            }
        });

        recetteViewModel.getIngredientsForRecipe(recipeId).observe(this, ingredients -> {
            Log.d("RecipeDetailActivity", "Ingredients: " + ingredients);
            ingredientAdapter.submitList(ingredients);
        });


        // Gérer le clic du bouton
        Button addToShoppingListButton = findViewById(R.id.btn_add_to_shopping_list);
        addToShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter les ingrédients de la recette à la liste de courses
                List<IngredientEntity> ingredients =  ingredientDao.getIngredientsByRecipeId(recipeId);
                for (IngredientEntity ingredient : ingredients) {
                    ListeDeCoursesEntity listItem = new ListeDeCoursesEntity(ingredient.getId());
                    listItem.setUserId(loggedInUser.getId());
                    listItem.setIngredientId(ingredient.getId());
                    listItem.setQuantite(ingredient.getQuantite());
                    listesDeCoursesDao.insertListe(listItem);
                    Toast.makeText(RecipeDetailActivity.this, "Info : " + ingredient.getNom() +" : " + ingredient.getQuantite(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(RecipeDetailActivity.this, "Ingrédients " + ingredients, Toast.LENGTH_SHORT).show();

                // Naviguez vers ListeDeCourseActivity
                //Intent intent = new Intent(RecipeDetailActivity.this, ListeDeCourseActivity.class);
                //startActivity(intent);
            }
        });
    }
}
