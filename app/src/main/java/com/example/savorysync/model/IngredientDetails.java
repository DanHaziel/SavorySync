package com.example.savorysync.model;

public class IngredientDetails {
    private String nom;
    private int totalQuantite;
    private String unite;

    // Constructor
    public IngredientDetails(String nom, int totalQuantite, String unite) {
        this.nom = nom;
        this.totalQuantite = totalQuantite;
        this.unite = unite;
    }

    // Getters and Setters
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
