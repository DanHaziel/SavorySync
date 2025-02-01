package com.example.savorysync.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.entity.RecetteEntity;
import com.example.savorysync.viewmodel.ShoppingListViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private ShoppingListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*AppDatabase db = AppDatabase.getDatabaseFromJava(this);
        List<LocalDate> dateListe = db.listeDeCoursesDao().getListesByDate();*/

        AppDatabase db = AppDatabase.getDatabaseFromJava(this);
        LoginEntity loggedInUser = db.loginDao().getLoggedInUser(); // Récupérer l'utilisateur connecté

        List<LocalDate> dateListe = db.listeDeCoursesDao().getAllDatesForUser(loggedInUser.getId());


        ShoppingListAdapter adapter = new ShoppingListAdapter(this, R.layout.item_option, dateListe, date -> {
            Intent intent = new Intent(this, ListeDeCourseActivity.class);
            intent.putExtra("DATE_CREATION", date.toString()); // Pass the date
            Log.d("date", date.toString());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        viewModel.getAllDistinctDates().observe(this, dates -> {
            adapter.updateData(dates);
        });
    }
}