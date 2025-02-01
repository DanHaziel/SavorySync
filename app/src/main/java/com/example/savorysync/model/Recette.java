package com.example.savorysync.model;

import android.util.Log;

import com.example.savorysync.model.entity.RecetteEntity;

import java.util.ArrayList;

public class Recette {

    private ArrayList<RecetteEntity> recettes;

    public Recette() {
        recettes = new ArrayList<>();
        init(); // Initialisation automatique des modules
    }

    public void ajoute(RecetteEntity recette) {
        recettes.add(recette);
        Log.d("Recettes", "Recettes initiales: " + recette);
    }

    public void init() {
        // Ajout des modules avec leurs données initiales
        ajoute(new RecetteEntity("Pasta Bolognese", "Italian",
                "[{\"name\":\"Pasta\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"Tomato Sauce\",\"quantity\":300,\"unit\":\"ml\"}]",
                "Cook pasta, add sauce, mix well.", 4, false, 1));
        ajoute(new RecetteEntity("Caesar Salad", "Salad",
                "[{\"name\":\"Lettuce\",\"quantity\":1,\"unit\":\"head\"},{\"name\":\"Croutons\",\"quantity\":50,\"unit\":\"g\"}]",
                "Mix lettuce and croutons. Add dressing.", 2, false, 1));
        ajoute(new RecetteEntity("Porc Rôti", "Cuisine africaine",
                "[{\"name\":\"Porc\",\"quantity\":500,\"unit\":\"g\"}," +
                        "{\"name\":\"Poireaux\",\"quantity\":2,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Poivron\",\"quantity\":1,\"unit\":\"piece\"}," +
                        "{\"name\":\"Tomates\",\"quantity\":3,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Oignons\",\"quantity\":2,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Ails\",\"quantity\":3,\"unit\":\"gousses\"}," +
                        "{\"name\":\"Sel\",\"quantity\":1,\"unit\":\"pincee\"}," +
                        "{\"name\":\"Cube\",\"quantity\":1,\"unit\":\"piece\"}," +
                        "{\"name\":\"Poivre\",\"quantity\":1,\"unit\":\"pincee\"}]",
                "Découpez le porc, nettoyez avec du citron, rincez, puis mettez dans une marmite. " +
                        "Ajoutez les oignons coupés, l’ail, le sel, et 1/4 de verre d’eau. Faites cuire jusqu’à ce que la viande soit tendre, puis faites-la frire. " +
                        "Découpez les poireaux, poivron, tomates, et oignons en petits morceaux. Versez-les dans une marmite. Remuez jusqu’à ce que les tomates sèchent. " +
                        "Ajoutez de l’huile de palme, du sel, du poivre, et du cube. Remuez bien, goûtez et ajustez l’assaisonnement. " +
                        "Ajoutez la viande de porc frite, mélangez, et laissez mijoter à feu doux pendant 10 minutes.",
                4, false, 1));
        ajoute(new RecetteEntity("Condiments Parfaits pour Assaisonner", "Sauces et Assaisonnements",
                "[{\"name\":\"Poireaux\",\"quantity\":2,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Persil\",\"quantity\":1,\"unit\":\"bouquet\"}," +
                        "{\"name\":\"Coriandre\",\"quantity\":1,\"unit\":\"bouquet\"}," +
                        "{\"name\":\"Oignons\",\"quantity\":2,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Poivrons verts\",\"quantity\":2,\"unit\":\"pieces\"}," +
                        "{\"name\":\"Ail\",\"quantity\":2,\"unit\":\"gousses\"}]",
                "1. Préparez les ingrédients : Lavez soigneusement les poireaux, persil, coriandre, oignons, poivrons verts, et l'ail.\n" +
                        "2. Mixez : Coupez les ingrédients en morceaux et mixez-les avec un peu d’eau jusqu’à obtenir une pâte homogène.\n" +
                        "\nDeux méthodes d’utilisation :\n" +
                        "Méthode 1 (Assaisonnement direct) : Mélangez la pâte fraîchement mixée avec du sel, des cubes et des épices. Appliquez directement sur le poulet, le porc ou le poisson.\n" +
                        "Méthode 2 (Pré-cuisson des condiments - recommandée) : Faites cuire la pâte obtenue à feu doux avec du sel, des cubes et des épices. Remuez régulièrement et ajustez l’assaisonnement. La cuisson est terminée lorsque la pâte devient plus foncée et que la couleur verte remonte à la surface.\n" +
                        "\nAstuce : Préférez la méthode 2 pour des saveurs plus développées et équilibrées.",
                6, false, 1));
    }

    public ArrayList<RecetteEntity> getInitialRecettes() {
        return recettes; // Retourne la liste complète des modules
    }
}
