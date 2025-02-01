package com.example.savorysync.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "table_ingredient",
        foreignKeys = @ForeignKey(entity = RecetteEntity.class,
                parentColumns = "id",
                childColumns = "idRecette",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "idRecette")})
public class IngredientEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "quantite")
    private int quantite;

    @ColumnInfo(name = "unite")
    private String unite;

    @ColumnInfo(name = "idRecette")
    private int idRecette; // Foreign key to RecetteEntity

    // Constructor
    public IngredientEntity(String nom, int quantite, String unite, int idRecette) {
        this.nom = nom;
        this.quantite = quantite;
        this.unite = unite;
        this.idRecette = idRecette;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", unite='" + unite + '\'' +
                ", idRecette=" + idRecette +
                '}';
    }
}
