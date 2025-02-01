package com.example.savorysync.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.dao.ListeDeCoursesDao;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.AppDatabase;

import java.util.List;

public class ListeDeCoursesRepository {
    private final ListeDeCoursesDao listeDeCoursesDao;
    private final LiveData<List<ListeDeCoursesEntity>> allListeDeCourse;

    public ListeDeCoursesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabaseFromJava(application);
        listeDeCoursesDao = db.listeDeCoursesDao();
        allListeDeCourse = listeDeCoursesDao.getAllShoppingLists();
    }

    public LiveData<List<ListeDeCoursesEntity>> getAllShoppingLists() {
        return allListeDeCourse;
    }

    public void insert(ListeDeCoursesEntity listeDeCoursesEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> listeDeCoursesDao.insertListe(listeDeCoursesEntity));
    }

    public void delete(ListeDeCoursesEntity listeDeCoursesEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> listeDeCoursesDao.delete(listeDeCoursesEntity));
    }
}
