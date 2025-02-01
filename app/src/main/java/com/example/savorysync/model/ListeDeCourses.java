package com.example.savorysync.model;

import android.annotation.SuppressLint;

import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;

import java.time.LocalDate;

public class ListeDeCourses {

    @SuppressLint("NewApi")
    public static void populateListeDeCourses(AppDatabase db) {
        ListeDeCoursesDao listeDeCoursesDao = db.listeDeCoursesDao();

        // Suppression des données existantes pour éviter les doublons
        listeDeCoursesDao.deleteAll();

        // Ajouter des éléments à la liste de courses
        ListeDeCoursesEntity item1 = new ListeDeCoursesEntity(1); // ID de l'ingrédient
        item1.setUserId(1); // ID de l'utilisateur
        item1.setQuantite(2); // Quantité
        item1.setDateCreation(LocalDate.now());

        ListeDeCoursesEntity item2 = new ListeDeCoursesEntity(2);
        item2.setUserId(1);
        item2.setQuantite(1);
        item2.setDateCreation(LocalDate.now());

        ListeDeCoursesEntity item3 = new ListeDeCoursesEntity(3);
        item3.setUserId(2);
        item3.setQuantite(5);
        item3.setDateCreation(LocalDate.now().minusDays(1));

        // Insérer les éléments dans la base de données
        listeDeCoursesDao.insertListe(item1);
        listeDeCoursesDao.insertListe(item2);
        listeDeCoursesDao.insertListe(item3);
    }
}
