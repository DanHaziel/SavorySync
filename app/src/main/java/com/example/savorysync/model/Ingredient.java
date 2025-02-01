package com.example.savorysync.model;

import android.util.Log;

import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ingredient {

    private ArrayList<IngredientEntity> ingredients;

    public Ingredient() {
        ingredients = new ArrayList<>();
        init(); // Initialisation automatique des ingrédients
    }

    private void init() {
        // Initialisation des recettes
        Recette recetteManager = new Recette();
        ArrayList<RecetteEntity> recettes = recetteManager.getInitialRecettes();
        Log.d("Recettes : ", recettes.toString());

        for (RecetteEntity recette : recettes) {
            try {
                JSONArray ingredientArray = new JSONArray(recette.getIngredients());
                for (int i = 0; i < ingredientArray.length(); i++) {
                    JSONObject ingredientJson = ingredientArray.getJSONObject(i);

                    String nom = ingredientJson.getString("name");
                    int quantite = ingredientJson.getInt("quantity");
                    String unite = ingredientJson.getString("unit");

                    // Ajout de l'ingrédient avec la clé étrangère correspondant à la recette
                    IngredientEntity ingredient = new IngredientEntity(nom, quantite, unite, recette.getId());
                    ingredients.add(ingredient);

                    // Log pour vérifier l'ingrédient ajouté
                    Log.d("Ingredient", "Ajouté: " + ingredient);
                }
            } catch (JSONException e) {
                Log.e("Ingredient", "Erreur lors de la lecture des ingrédients: ", e);
            }
        }
    }

    public ArrayList<IngredientEntity> getInitialIngredients() {
        return ingredients; // Retourne la liste complète des ingrédients
    }
}
