package com.example.savorysync.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import androidx.annotation.NonNull;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.dao.LoginDao;
import com.example.savorysync.model.dao.RecetteDao;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import com.example.savorysync.model.entity.Converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RecetteEntity.class, ListeDeCoursesEntity.class, IngredientEntity.class, LoginEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecetteDao recetteDao();
    public abstract ListeDeCoursesDao listeDeCoursesDao();
    public abstract IngredientDao ingredientDao();
    public abstract LoginDao loginDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabaseFromJava(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "recipe_database.db")
                            .addCallback(databaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }else{
            Log.d("Instance", INSTANCE.toString());
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Obtenez les DAO
                RecetteDao recetteDao = INSTANCE.recetteDao();
                ListeDeCoursesDao listeDeCoursesDao = INSTANCE.listeDeCoursesDao();
                IngredientDao ingredientDao = INSTANCE.ingredientDao();
                LoginDao loginDao = INSTANCE.loginDao();

                // Supprimez les données existantes si nécessaire
                loginDao.deleteAll();
                recetteDao.deleteAll();
                ingredientDao.deleteAll();
                listeDeCoursesDao.deleteAll();

                // Ajoutez vos données initiales
                Utilisateur.populateUsers(INSTANCE);

                Recette recettes = new Recette();
                for (RecetteEntity recette : recettes.getInitialRecettes()) {
                    long recetteId = recetteDao.insertRecette(recette); // Retourne l'ID généré
                    Log.d("DatabaseInsertion", "Recette insérée avec ID: " + recetteId);

                    try {
                        // Insérer les ingrédients liés à cette recette
                        JSONArray ingredientArray = new JSONArray(recette.getIngredients());
                        for (int i = 0; i < ingredientArray.length(); i++) {
                            JSONObject ingredientJson = ingredientArray.getJSONObject(i);
                            String nom = ingredientJson.getString("name");
                            int quantite = ingredientJson.getInt("quantity");
                            String unite = ingredientJson.getString("unit");

                            IngredientEntity ingredient = new IngredientEntity(nom, quantite, unite, (int) recetteId);
                            ingredientDao.insertIngredient(ingredient);
                            Log.d("IngredientInsertion", "Insertion de l'ingrédient avec idRecette=" + ingredient.getIdRecette());
                            Log.d("DatabaseInsertion", "Ingrédient inséré: " + ingredient);
                        }
                    } catch (JSONException e) {
                        Log.e("DatabaseInsertion", "Erreur JSON: ", e);
                    }
                }

                ListeDeCourses.populateListeDeCourses(INSTANCE);
                Log.d("InsertionListe", "fin34");
            });
        }
    };
}
