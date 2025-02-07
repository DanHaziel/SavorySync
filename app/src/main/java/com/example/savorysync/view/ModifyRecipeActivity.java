package com.example.savorysync.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.dao.RecetteDao;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.List;

public class ModifyRecipeActivity extends AppCompatActivity {

    private EditText editNom, editPersonnes, editInstructions, editCategorie;
    private Button btnSave;
    private LinearLayout ingredientsLayout;
    private RecetteDao recetteDao;
    private IngredientDao ingredientDao;
    private int recipeId;
    private List<IngredientEntity> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);

        editNom = findViewById(R.id.edit_nom);
        editCategorie = findViewById(R.id.edit_categorie);
        editPersonnes = findViewById(R.id.edit_personnes);
        editInstructions = findViewById(R.id.edit_instructions);
        btnSave = findViewById(R.id.btn_save_recipe);
        ingredientsLayout = findViewById(R.id.ingredientsLayout);

        recetteDao = AppDatabase.getDatabaseFromJava(this).recetteDao();
        ingredientDao = AppDatabase.getDatabaseFromJava(this).ingredientDao();

        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        if (recipeId == -1) {
            Toast.makeText(this, "Erreur : Recette introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRecipeDetails();
        loadIngredients();

        btnSave.setOnClickListener(v -> saveChanges());
    }

    private void loadRecipeDetails() {
        new Thread(() -> {
            RecetteEntity recette = recetteDao.getRecipeByIdSync(recipeId);
            runOnUiThread(() -> {
                if (recette != null) {
                    editNom.setText(recette.getTitre());
                    editCategorie.setText(recette.getCategorie());
                    editPersonnes.setText(String.valueOf(recette.getPersonnes()));
                    editInstructions.setText(recette.getInstructions());
                } else {
                    Toast.makeText(this, "Erreur : Recette introuvable", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
    }

    private void loadIngredients() {
        new Thread(() -> {
            ingredientList = ingredientDao.getIngredientsByRecipeId(recipeId);
            runOnUiThread(() -> {
                ingredientsLayout.removeAllViews();
                for (IngredientEntity ingredient : ingredientList) {
                    addIngredientRow(ingredient);
                }
            });
        }).start();
    }

    private void addIngredientRow(IngredientEntity ingredient) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText editIngredientName = new EditText(this);
        editIngredientName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
        editIngredientName.setHint("Nom de l'ingrédient");
        editIngredientName.setText(ingredient.getNom());
        editIngredientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText editIngredientQuantity = new EditText(this);
        editIngredientQuantity.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        editIngredientQuantity.setHint("Quantité");
        editIngredientQuantity.setText(String.valueOf(ingredient.getQuantite()));
        editIngredientQuantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        EditText editIngredientUnit = new EditText(this);
        editIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
        editIngredientUnit.setHint("Unité");
        editIngredientUnit.setText(ingredient.getUnite());
        editIngredientUnit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        rowLayout.addView(editIngredientName);
        rowLayout.addView(editIngredientQuantity);
        rowLayout.addView(editIngredientUnit);

        ingredientsLayout.addView(rowLayout);

        // Sauvegarde des modifications locales pour chaque ingrédient
        editIngredientName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ingredient.setNom(editIngredientName.getText().toString().trim());
            }
        });

        editIngredientQuantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ingredient.setQuantite(Integer.parseInt(editIngredientQuantity.getText().toString().trim()));
            }
        });

        editIngredientUnit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ingredient.setUnite(editIngredientUnit.getText().toString().trim());
            }
        });
    }

    private void saveChanges() {
        String newNom = editNom.getText().toString().trim();
        String newPersonnesStr = editPersonnes.getText().toString().trim();
        String newInstructions = editInstructions.getText().toString().trim();
        String newCategorie = editCategorie.getText().toString().trim();

        if (newNom.isEmpty() || newPersonnesStr.isEmpty() || newInstructions.isEmpty() || newCategorie.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int newPersonnes = Integer.parseInt(newPersonnesStr);

        new Thread(() -> {
            RecetteEntity recette = recetteDao.getRecipeByIdSync(recipeId);
            if (recette != null) {
                recette.setTitre(newNom);
                recette.setCategorie(newCategorie);
                recette.setPersonnes(newPersonnes);
                recette.setInstructions(newInstructions);
                recetteDao.updateRecipe(recette);

                // Mise à jour des ingrédients
                for (IngredientEntity ingredient : ingredientList) {
                    ingredientDao.updateIngredient(ingredient);
                }

                runOnUiThread(() -> {
                    Toast.makeText(this, "Recette mise à jour !", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}
