package com.example.savorysync.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.IngredientDetails;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.entity.LoginEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModifyListActivity extends AppCompatActivity {

    private ListeDeCoursesDao listesDeCoursesDao;
    private IngredientDao ingredientDao;
    private Button btnSave;
    ModifyListAdapter adapter;
    String dateCreation;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_list);

        btnSave = findViewById(R.id.btn_save_list);

        //int dateCreation = getIntent().getIntExtra("DATE_CREATION", -1);
        Intent intent = getIntent();
        // Récupérer la valeur associée à la clé "DATE_CREATION"
        dateCreation = intent.getStringExtra("DATE_CREATION");
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
        adapter = new ModifyListAdapter(this, R.layout.item_modify_shopping_list, new ArrayList());
        recyclerView.setAdapter(adapter);

        // Observez les données de la liste de courses
        Log.d("ModifyListActivity", "Date reçue : " + dateCreation);
        LiveData<List<IngredientDetails>> shoppingList = listesDeCoursesDao.getGroupedIngredientsByDateAndUser(LocalDate.parse(dateCreation), loggedInUser.getId());
        shoppingList.observe(this, adapter::submitList);

        btnSave.setOnClickListener(v -> saveChanges());
    }

    @SuppressLint("NewApi")
    private void saveChanges() {
        if (adapter == null || adapter.getItemCount() == 0) {
            Toast.makeText(this, "Aucune modification à enregistrer.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<IngredientDetails> modifiedList = adapter.getUpdatedList();

        new Thread(() -> {
            for (IngredientDetails ingredient : modifiedList) {
                listesDeCoursesDao.updateIngredientDetails(
                        ingredient.getId(),
                        ingredient.getTotalQuantite(),
                        LocalDate.parse(dateCreation)
                );
                Log.e("ModifyListActivity", "Ingredient : " + ingredient.getTotalQuantite() + " ; id : " + ingredient.getId());
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Liste mise à jour avec succès !", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

}