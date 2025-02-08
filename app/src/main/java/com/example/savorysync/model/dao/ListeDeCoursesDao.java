package com.example.savorysync.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savorysync.model.IngredientDetails;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ListeDeCoursesDao {

    @Insert
    void insertListe(ListeDeCoursesEntity listeDeCourses);

    @Update
    void update(ListeDeCoursesEntity listeDeCourses);

    @Delete
    void delete(ListeDeCoursesEntity listeDeCourses);

    @Query("SELECT * FROM table_liste_de_courses ORDER BY dateCreation DESC")
    LiveData<List<ListeDeCoursesEntity>> getAllShoppingLists();

    @Query("SELECT * FROM table_liste_de_courses")
    LiveData<List<ListeDeCoursesEntity>> getAllShoppingListItems();

    @Query("SELECT DISTINCT dateCreation " +
            "FROM table_liste_de_courses " +
            "WHERE userId = :userId " +
            "ORDER BY dateCreation DESC")
    LiveData<List<LocalDate>> getAllDistinctDates(int userId);

    @Query("SELECT DISTINCT dateCreation FROM table_liste_de_courses ORDER BY dateCreation ASC")
    List<LocalDate> getListesByDate();

    @Query("SELECT * FROM table_liste_de_courses WHERE dateCreation=:dateCreation ORDER BY id ASC")
    LiveData<List<ListeDeCoursesEntity>> getAllListeForDate(LocalDate dateCreation);

    @Query("SELECT * FROM table_liste_de_courses WHERE id = :id")
    LiveData<ListeDeCoursesEntity> getShoppingListById(int id);

    @Query("DELETE FROM table_liste_de_courses WHERE id = :id")
    void deleteShoppingListById(int id);

    @Query("SELECT i.id, i.nom, SUM(l.quantite) AS totalQuantite, i.unite " +
            "FROM table_liste_de_courses l " +
            "JOIN table_ingredient i ON l.ingredientId = i.id " +
            "WHERE l.dateCreation = :date " +
            "GROUP BY i.nom, i.unite " +
            "ORDER BY i.nom ASC")
    LiveData<List<IngredientDetails>> getIngredientsDetailsByDate(LocalDate date);

    @Query("SELECT i.id, i.nom, SUM(l.quantite) AS totalQuantite, i.unite " +
            "FROM table_liste_de_courses l " +
            "JOIN table_ingredient i ON l.ingredientId = i.id " +
            "WHERE l.dateCreation = :date AND l.userId = :userId " +
            "GROUP BY i.nom, i.unite " +
            "ORDER BY i.nom ASC")
    LiveData<List<IngredientDetails>> getGroupedIngredientsByDateAndUser(LocalDate date, int userId);

    @Query("SELECT DISTINCT dateCreation " +
            "FROM table_liste_de_courses " +
            "WHERE userId = :userId " +
            "ORDER BY dateCreation DESC")
    List<LocalDate> getAllDatesForUser(int userId);

    @Query("DELETE FROM table_liste_de_courses")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM table_liste_de_courses")
    int getRowCount();
}
