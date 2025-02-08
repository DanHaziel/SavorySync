package com.example.savorysync.model;

public class IngredientDetails {
    private int id;
    private String nom;
    private int totalQuantite;
    private String unite;

    // Constructor
    public IngredientDetails(int id, String nom, int totalQuantite, String unite) {
        this.id = id;
        this.nom = nom;
        this.totalQuantite = totalQuantite;
        this.unite = unite;
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

    public int getTotalQuantite() {
        return totalQuantite;
    }

    public void setTotalQuantite(int totalQuantite) {
        this.totalQuantite = totalQuantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Override
    public String toString() {
        return "IngredientDetails{" +
                "nom='" + nom + '\'' +
                ", totalQuantite=" + totalQuantite +
                ", unite='" + unite + '\'' +
                '}';
    }
}
