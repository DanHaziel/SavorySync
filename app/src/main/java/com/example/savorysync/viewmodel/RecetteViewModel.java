package com.example.savorysync.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.RecetteEntity;
import com.example.savorysync.model.repository.RecetteRepository;

import java.util.List;

public class RecetteViewModel extends AndroidViewModel {

    private final RecetteRepository repository;
    private final LiveData<List<RecetteEntity>> allRecipes, allRecipesForUser;
    //private final LiveData<RecetteEntity> recipeById;
    IngredientDao ingredientDao;

    public RecetteViewModel(Application application) {
        super(application);
        repository = new RecetteRepository(application);
        allRecipes = repository.getAllRecipes();
        allRecipesForUser = repository.getAllRecipesForUser();
        //recipeById = repository.getRecipeById();
        AppDatabase db = AppDatabase.getDatabaseFromJava(application);
        ingredientDao = db.ingredientDao();
    }

    public LiveData<List<RecetteEntity>> getAllRecipes() {
        return allRecipes;
    }

    public LiveData<List<RecetteEntity>> getAllRecipesForUser() {
        return allRecipesForUser;
    }

    /*public LiveData<RecetteEntity> getRecipeById() {
        return recipeById;
    }*/

    public LiveData<List<IngredientEntity>> getIngredientsForRecipe(int recipeId) {
        return ingredientDao.getAllByRecipeId(recipeId);
    }


    public void insert(RecetteEntity recetteEntity) {
        repository.insert(recetteEntity);
    }

    public void delete(RecetteEntity recetteEntity) {
        repository.delete(recetteEntity);
    }
}
