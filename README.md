# SavorySync - Application de Gestion de Recettes

## Description

SavorySync est une application Android permettant aux utilisateurs de gérer leurs recettes culinaires et leurs listes de courses. Elle offre la possibilité d'ajouter, consulter et organiser des recettes, tout en optimisant la gestion des ingrédients nécessaires à leur réalisation.

## Fonctionnalités

- Ajouter et gérer des recettes avec leurs ingrédients.
- Générer automatiquement une liste de courses en fonction des recettes sélectionnées.
- Stocker et récupérer les données grâce à une base de données locale.
- Interface utilisateur ergonomique et intuitive.

## Architecture

Le projet suit une architecture **MVVM (Model-View-ViewModel)** pour assurer une séparation claire des responsabilités :

### **Model**

- `entity/` : Contient les classes de données représentant les entités de la base de données.
- `dao/` : Définit les interfaces pour interagir avec la base de données SQLite via Room.
- `repository/` : Centralise la gestion des données et assure la communication entre le ViewModel et la base de données.

### **View**

- Contient toutes les activités et les adaptateurs RecyclerView nécessaires pour afficher les données à l'utilisateur.

### **ViewModel**

- Gère la logique de présentation et interagit avec le repository pour récupérer les données.

## Technologies utilisées

- **Langage** : Java
- **Base de données locale** : Room (SQLite)
- **Architecture** : MVVM
- **Interface utilisateur** : ConstraintLayout, RecyclerView
- **Gestion des données en temps réel** : LiveData

## Installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/votre-repo/SavorySync.git
   ```
2. **Ouvrir avec Android Studio**
    - Lancer Android Studio et ouvrir le projet.
3. **Reconstruire le projet**
    - Aller dans `Build` > `Rebuild Project`
4. **Exécuter l'application**
    - Connecter un émulateur ou un appareil physique.
    - Cliquer sur "Run".

## Exclusion du dossier `build`

Le dossier `app/build` est exclu du projet pour éviter de transmettre des fichiers inutiles. Assurez-vous de reconstruire le projet après clonage.

## Améliorations futures

- Ajout d'une fonctionnalité de partage de recettes entre utilisateurs.
- Mise en place d'une barre de recherche pour trouver des recettes plus facilement.
- Implémentation d'une gestion avancée des permissions et de la sécurité des données.

## Auteur

- **NGATCHUISSI MBOUCHE Dan** - [@DanHaziel](https://github.com/DanHaziel) - Développeur Android

---

**SavorySync** - Simplifiez la gestion de vos recettes et de vos courses culinaires ! 🍽️



