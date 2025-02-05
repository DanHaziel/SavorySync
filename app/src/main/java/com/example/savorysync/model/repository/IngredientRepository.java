package com.example.savorysync.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.entity.IngredientEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IngredientRepository {
    private final IngredientDao ingredientDao;
    private final ExecutorService executorService;

    public IngredientRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabaseFromJava(application);
        ingredientDao = database.ingredientDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Insert a new ingredient
    public void insertIngredient(IngredientEntity ingredient) {
        executorService.execute(() -> ingredientDao.insertIngredient(ingredient));
    }

    // Update an existing ingredient
    public void updateIngredient(IngredientEntity ingredient) {
        executorService.execute(() -> ingredientDao.updateIngredient(ingredient));
    }

    // Delete a specific ingredient
    public void deleteIngredient(IngredientEntity ingredient) {
        executorService.execute(() -> ingredientDao.delete(ingredient));
    }

    // Get all ingredients for a specific shopping list
    /*public LiveData<List<IngredientEntity>> getIngredientsForShoppingList(int idListe) {
        return ingredientDao.getIngredientsForShoppingList(idListe);
    }*/

    // Get a specific ingredient by ID
    public LiveData<IngredientEntity> getIngredientById(int id) {
        return ingredientDao.getIngredientById(id);
    }

    // Delete all ingredients for a specific shopping list
    /*public void deleteAllIngredientsForShoppingList(int idListe) {
        executorService.execute(() -> ingredientDao.deleteAllIngredientsForShoppingList(idListe));
    }*/

    // Get an ingredient by name and shopping list
    /*public IngredientEntity getIngredientByNameAndList(String nom, int idListe) {
        return ingredientDao.getIngredientByNameAndList(nom, idListe);
    }*/

    // Update quantity for a specific ingredient
    public void updateQuantity(int id, int quantite) {
        executorService.execute(() -> ingredientDao.updateQuantity(id, quantite));
    }

    // Delete all ingredients
    public void deleteAllIngredients() {
        executorService.execute(ingredientDao::deleteAll);
    }
}
