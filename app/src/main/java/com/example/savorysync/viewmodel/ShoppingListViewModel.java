package com.example.savorysync.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.entity.LoginEntity;

import java.time.LocalDate;
import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private final ListeDeCoursesDao listeDeCoursesDao;
    private final LoginEntity loggedInUser;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabaseFromJava(application);
        listeDeCoursesDao = db.listeDeCoursesDao();
        loggedInUser = db.loginDao().getLoggedInUser(); // Récupérer l'utilisateur connecté
    }

    public LiveData<List<LocalDate>> getAllDistinctDates() {
        return listeDeCoursesDao.getAllDistinctDates(loggedInUser.getId());
    }
}
