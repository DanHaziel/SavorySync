package com.example.savorysync.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_recette",
        foreignKeys = @ForeignKey(entity = RecetteEntity.class,
                parentColumns = "id",
                childColumns = "idUtilisateur",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "idUtilisateur")})
public class RecetteEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "titre")
    private String titre;

    @ColumnInfo(name = "categorie")
    private String categorie;

    @ColumnInfo(name = "ingredients")
    private String ingredients; // JSON format or another table relation

    @ColumnInfo(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "personnes")
    private int personnes;

    @ColumnInfo(name = "isDeleted")
    private boolean isDeleted;

    @ColumnInfo(name = "idUtilisateur")
    private int idUtilisateur; // Foreign key to RecetteEntity
    // Constructor
    public RecetteEntity(String titre, String categorie, String ingredients, String instructions, int personnes, boolean isDeleted, int idUtilisateur) {
        this.titre = titre;
        this.categorie = categorie;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.personnes = personnes;
        this.isDeleted = isDeleted;
        this.idUtilisateur = idUtilisateur;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPersonnes() {
        return personnes;
    }

    public void setPersonnes(int personnes) {
        this.personnes = personnes;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public String toString() {
        return "Recette{" +
                "id=" + id +
                ", titre= '" + titre + '\'' +
                ", categorie= '" + categorie + '\'' +
                ", ingredients= '" + ingredients + '\'' +
                ", instructions= '" + instructions + '\'' +
                ", personnes= '" + personnes + '\'' +
                ", isDeleted= '" + isDeleted + '\'' +
                ", idUtilisateur= '" + idUtilisateur + '\'' +
                '}';
    }
}
