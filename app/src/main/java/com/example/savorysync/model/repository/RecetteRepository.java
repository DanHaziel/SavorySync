package com.example.savorysync.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;
import com.example.savorysync.model.dao.RecetteDao;
import com.example.savorysync.model.AppDatabase;

import java.util.List;

public class RecetteRepository {
    private final RecetteDao recetteDao;
    private final LiveData<List<RecetteEntity>> allRecipes, allRecipesForUser;
    //private final LiveData<RecetteEntity> recipeById;

    public RecetteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabaseFromJava(application);
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser();
        recetteDao = db.recetteDao();
        allRecipes = recetteDao.getAllRecipes();
        allRecipesForUser = recetteDao.getAllRecipesForUser(loggedInUser.getId());
        //recipeById = recetteDao.getRecipeById();
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

    public void insert(RecetteEntity recetteEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> recetteDao.insertRecette(recetteEntity));
    }

    public void delete(RecetteEntity recetteEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> recetteDao.delete(recetteEntity));
    }
}
