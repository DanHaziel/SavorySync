package com.example.savorysync.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savorysync.model.entity.RecetteEntity;

import java.util.List;

@Dao
public interface RecetteDao {

    @Insert
    long insertRecette(RecetteEntity recette);

    @Update
    void updateRecipe(RecetteEntity recette);

    @Delete
    void delete(RecetteEntity recette);

    @Query("SELECT * FROM table_recette WHERE isDeleted = 0 ORDER BY titre ASC")
    LiveData<List<RecetteEntity>> getAllRecipes();

    @Query("SELECT * FROM table_recette WHERE isDeleted = 0 AND idUtilisateur = :idUtilisateur ORDER BY titre ASC")
    LiveData<List<RecetteEntity>> getAllRecipesForUser(int idUtilisateur);

    @Query("SELECT * FROM table_recette WHERE isDeleted = 0 AND idUtilisateur = :idUtilisateur ORDER BY titre ASC")
    List<RecetteEntity> getRecipesByUserId(int idUtilisateur);

    @Query("SELECT * FROM table_recette WHERE id = :id")
    LiveData<List<RecetteEntity>> getRecipeById(int id);

    @Query("SELECT * FROM table_recette WHERE isDeleted = 1 ORDER BY titre ASC")
    LiveData<List<RecetteEntity>> getDeletedRecipes();

    @Query("UPDATE table_recette SET isDeleted = 1 WHERE id = :id")
    void softDeleteRecipe(int id);

    @Query("UPDATE table_recette SET isDeleted = 0 WHERE id = :id")
    void restoreRecipe(int id);

    @Query("DELETE FROM table_recette")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM table_recette")
    int getRowCount();

    @Query("SELECT * FROM table_recette WHERE id = :id")
    RecetteEntity getRecipeByIdSync(int id); // Pour exécution en arrière-plan
}
