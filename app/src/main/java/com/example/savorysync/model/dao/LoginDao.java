package com.example.savorysync.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.savorysync.model.entity.LoginEntity;

import java.util.List;

@Dao
public interface LoginDao {
    @Insert
    void insertDetails(LoginEntity data);

    @Delete
    void delete(LoginEntity login);

    @Query("SELECT * FROM table_connexion WHERE Email = :email AND Password = :password")
    LoginEntity login(String email, String password);

    @Query("select * from table_connexion")
    LiveData<List<LoginEntity>> getDetails();

    @Query("delete from table_connexion")
    void deleteAll();

    @Query("SELECT * FROM table_connexion WHERE isLoggedIn = 1 LIMIT 1")
    LoginEntity getLoggedInUser();

    @Query("UPDATE table_connexion SET isLoggedIn = 0")
    void logoutAllUsers();

    @Query("UPDATE table_connexion SET isLoggedIn = 1 WHERE Email = :email")
    void loginUser(String email);

    @Query("DELETE FROM table_connexion")
    void deleteAllLogins();

    @Query("SELECT COUNT(*) FROM table_connexion")
    int getRowCount();
}
