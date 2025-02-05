package com.example.savorysync.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savorysync.model.entity.IngredientEntity;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    void insertIngredient(IngredientEntity ingredient);

    @Update
    void updateIngredient(IngredientEntity ingredient);

    @Delete
    void delete(IngredientEntity ingredient);

    /*@Query("SELECT * FROM table_ingredient WHERE idListe = :idListe ORDER BY nom ASC")
    LiveData<List<IngredientEntity>> getIngredientsForShoppingList(int idListe);*/

    @Query("SELECT * FROM table_ingredient WHERE id = :id")
    LiveData<IngredientEntity> getIngredientById(int id);

    @Query("SELECT * FROM table_ingredient WHERE idRecette = :id")
    List<IngredientEntity> getIngredientsByRecipeId(int id);

    @Query("SELECT * FROM table_ingredient WHERE idRecette = :id")
    LiveData<List<IngredientEntity>> getAllByRecipeId(int id);

    @Query("UPDATE table_ingredient SET quantite = quantite + :quantite WHERE id = :id")
    void updateQuantity(int id, int quantite);

    @Query("DELETE FROM table_ingredient")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM table_ingredient")
    int getRowCount();
}
