package com.example.savorysync.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.IngredientDetails;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.entity.LoginEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListeDeCourseActivity extends AppCompatActivity {

    private ListeDeCoursesDao listesDeCoursesDao;
    private IngredientDao ingredientDao;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_de_course);

        //int dateCreation = getIntent().getIntExtra("DATE_CREATION", -1);
        Intent intent = getIntent();
            // Récupérer la valeur associée à la clé "DATE_CREATION"
            String dateCreation = intent.getStringExtra("DATE_CREATION");
            if (dateCreation != null) {
                // Utiliser la valeur
                Toast.makeText(this, "Date sélectionnée : " + dateCreation, Toast.LENGTH_SHORT).show();
            }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewShoppingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisez la base de données
        AppDatabase db = AppDatabase.getDatabaseFromJava(this.getApplicationContext());
        listesDeCoursesDao = db.listeDeCoursesDao();
        ingredientDao = db.ingredientDao();
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser(); // Récupérer l'utilisateur connecté
        //Log.d("Valeurs", "Valeurs : " + listesDeCoursesDao.getIngredientsDetailsByDate(LocalDate.parse(dateCreation)));
        // Configurez l'adapter
        ListeDeCoursesAdapter adapter = new ListeDeCoursesAdapter(this, R.layout.item_shopping_list, new ArrayList());
        recyclerView.setAdapter(adapter);

        // Observez les données de la liste de courses
        LiveData<List<IngredientDetails>> shoppingList = listesDeCoursesDao.getGroupedIngredientsByDateAndUser(LocalDate.parse(dateCreation), loggedInUser.getId());
        shoppingList.observe(this, adapter::submitList);
    }
}
