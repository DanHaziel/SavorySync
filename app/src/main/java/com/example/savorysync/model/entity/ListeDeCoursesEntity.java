package com.example.savorysync.model.entity;

import android.annotation.SuppressLint;

import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import com.example.savorysync.model.entity.Converters;

@Entity(tableName = "table_liste_de_courses",
        foreignKeys = @ForeignKey(
                entity = LoginEntity.class,
                parentColumns = "Id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index(value = "userId")})
public class ListeDeCoursesEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "ingredientId")
    private int ingredientId;

    @ColumnInfo(name = "quantite")
    private int quantite;

    @ColumnInfo(name = "dateCreation")
    @TypeConverters({Converters.class})
    private LocalDate dateCreation;

    // Constructor
    @SuppressLint("NewApi")
    public ListeDeCoursesEntity(int ingredientId) {
        this.ingredientId = ingredientId;
        this.dateCreation = LocalDate.now(); // Définit automatiquement la date actuelle
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "ListeDeCoursesEntity{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
