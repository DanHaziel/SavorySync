package com.example.savorysync.model;

import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.LoginEntity;

import java.util.ArrayList;

public class Utilisateur {

    public static void populateUsers(AppDatabase db) {
        // Supprimer les utilisateurs existants pour éviter les doublons (optionnel)
        db.loginDao().deleteAllLogins();

        // Créer les utilisateurs
        LoginEntity user1 = new LoginEntity();
        user1.setEmail("user1@example.com");
        user1.setPassword("password123");
        user1.setIsLoggedIn(false);

        LoginEntity user2 = new LoginEntity();
        user2.setEmail("user2@example.com");
        user2.setPassword("securepass456");
        user2.setIsLoggedIn(false);

        LoginEntity user3 = new LoginEntity();
        user3.setEmail("user3@example.com");
        user3.setPassword("mypassword789");
        user3.setIsLoggedIn(false);

        // Insérer les utilisateurs dans la base de données
        db.loginDao().insertDetails(user1);
        db.loginDao().insertDetails(user2);
        db.loginDao().insertDetails(user3);
    }
}
