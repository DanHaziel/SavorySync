package com.example.savorysync.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
    private Button modifyListButton, deleteListButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_de_course);

        modifyListButton = findViewById(R.id.btn_modify_list);
        deleteListButton = findViewById(R.id.btn_delete_list);
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

        modifyListButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(ListeDeCourseActivity.this, ModifyListActivity.class);
            //intent.putExtra("RECIPE_ID", recipeId);
            intent1.putExtra("DATE_CREATION", dateCreation);
            Log.d("Date",  dateCreation);
            startActivity(intent1);
        });

        deleteListButton.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Voulez-vous vraiment supprimer cette liste de courses ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        new Thread(() -> {
                            try {
                                int rowsDeleted = listesDeCoursesDao.deleteShoppingListByDateAndUser(LocalDate.parse(dateCreation), loggedInUser.getId());

                                runOnUiThread(() -> {
                                    if (rowsDeleted > 0) {
                                        Toast.makeText(this, "Liste supprimée avec succès", Toast.LENGTH_SHORT).show();

                                        // Redirige vers ShoppingListActivity
                                        Intent intent2 = new Intent(ListeDeCourseActivity.this, ShoppingListActivity.class);
                                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent2);
                                        finish(); // Ferme l'activité actuelle

                                    } else {
                                        Toast.makeText(this, "Aucune liste trouvée pour cette date", Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(() -> {
                                    Toast.makeText(this, "Erreur lors de la suppression : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                                e.printStackTrace();
                            }
                        }).start();
                    })
                    .setNegativeButton("Non", null) // Annuler la suppression
                    .show();
        });

    }
}
