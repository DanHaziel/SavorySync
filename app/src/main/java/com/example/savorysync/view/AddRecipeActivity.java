package com.example.savorysync.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText, personsEditText, instructionsEditText, categorieEditText;
    private LinearLayout ingredientsLayout;
    private Button addIngredientButton, saveRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialisation des vues
        recipeNameEditText = findViewById(R.id.editTextRecipeName);
        categorieEditText = findViewById(R.id.editTextCategorie);
        personsEditText = findViewById(R.id.editTextPersons);
        instructionsEditText = findViewById(R.id.editTextInstructions);
        ingredientsLayout = findViewById(R.id.ingredientsLayout);
        addIngredientButton = findViewById(R.id.buttonAddIngredient);
        saveRecipeButton = findViewById(R.id.buttonSaveRecipe);

        // Ajouter un ingrédient
        addIngredientButton.setOnClickListener(v -> addIngredientField());

        // Sauvegarder la recette
        saveRecipeButton.setOnClickListener(v -> saveRecipe());
    }

    private void addIngredientField() {
        View ingredientRow = getLayoutInflater().inflate(R.layout.item_ingredient_row, ingredientsLayout, false);
        ingredientsLayout.addView(ingredientRow);
    }

    private void saveRecipe() {
        String recipeName = recipeNameEditText.getText().toString().trim();
        String instructions = instructionsEditText.getText().toString().trim();
        String categorie = categorieEditText.getText().toString().trim();
        int persons;
        try {
            persons = Integer.parseInt(personsEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            persons = 0;
        }

        if (recipeName.isEmpty() || instructions.isEmpty() || persons <= 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<IngredientEntity> ingredients = new ArrayList<>();
        StringBuilder ingredientsJson = new StringBuilder("[");
        for (int i = 0; i < ingredientsLayout.getChildCount(); i++) {
            View row = ingredientsLayout.getChildAt(i);
            EditText nameEditText = row.findViewById(R.id.editTextIngredientName);
            EditText quantityEditText = row.findViewById(R.id.editTextIngredientQuantity);
            EditText unitEditText = row.findViewById(R.id.editTextIngredientUnit);

            String name = nameEditText.getText().toString().trim();
            String quantityText = quantityEditText.getText().toString().trim();
            String unit = unitEditText.getText().toString().trim();

            int quantity = 0;
            if (!quantityText.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quantityText);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Veuillez entrer une quantité valide", Toast.LENGTH_SHORT).show();
                }
            }

            if (!name.isEmpty() && quantity > 0 && !unit.isEmpty()) {
                ingredients.add(new IngredientEntity(name, quantity, unit, 3));

                // Ajouter cet ingrédient à la chaîne JSON
                ingredientsJson.append("{")
                        .append("\"name\":\"").append(name).append("\",")
                        .append("\"quantity\":\"").append(quantity).append("\",")
                        .append("\"unit\":\"").append(unit).append("\"")
                        .append("},");
            }
        }

        if (ingredients.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter au moins un ingrédient.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Supprimer la dernière virgule et fermer le tableau JSON
        if (ingredientsJson.charAt(ingredientsJson.length() - 1) == ',') {
            ingredientsJson.deleteCharAt(ingredientsJson.length() - 1);
        }
        ingredientsJson.append("]");

        // Sauvegarder dans la base de données
        AppDatabase db = AppDatabase.getDatabaseFromJava(this);
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser();

        RecetteEntity recetteEntity = new RecetteEntity(recipeName, categorie , ingredientsJson.toString(), instructions, persons, false, loggedInUser.getId());

        long recetteId = db.recetteDao().insertRecette(recetteEntity);

        for (IngredientEntity ingredient : ingredients) {
            ingredient.setIdRecette((int) recetteId);
            db.ingredientDao().insertIngredient(ingredient);
        }

        Toast.makeText(this, "Recette ajoutée avec succès !", Toast.LENGTH_SHORT).show();
        finish();
    }

}
