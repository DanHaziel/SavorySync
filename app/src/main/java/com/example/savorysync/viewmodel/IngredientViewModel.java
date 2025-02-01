package com.example.savorysync.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.repository.IngredientRepository;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private final IngredientRepository repository;
    //private final LiveData<List<IngredientEntity>> allIngredients;

    public IngredientViewModel(Application application) {
        super(application);
        repository = new IngredientRepository(application);
        //allIngredients = repository.getIngredientsForShoppingList(1); // Exemple avec une liste spécifique
    }

    /*public LiveData<List<IngredientEntity>> getAllIngredients() {
        return allIngredients;
    }*/

    public void insert(IngredientEntity ingredient) {
        repository.insertIngredient(ingredient);
    }

    public void update(IngredientEntity ingredient) {
        repository.updateIngredient(ingredient);
    }

    public void delete(IngredientEntity ingredient) {
        repository.deleteIngredient(ingredient);
    }

    public void updateQuantity(int id, int quantity) {
        repository.updateQuantity(id, quantity);
    }
}

