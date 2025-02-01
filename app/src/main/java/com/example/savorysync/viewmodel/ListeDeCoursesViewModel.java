package com.example.savorysync.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.repository.ListeDeCoursesRepository;

import java.util.List;

public class ListeDeCoursesViewModel extends AndroidViewModel {

    private final ListeDeCoursesRepository repository;
    private final LiveData<List<ListeDeCoursesEntity>> allShoppingLists;

    public ListeDeCoursesViewModel(Application application) {
        super(application);
        repository = new ListeDeCoursesRepository(application);
        allShoppingLists = repository.getAllShoppingLists();
    }

    public LiveData<List<ListeDeCoursesEntity>> getAllShoppingLists() {
        return allShoppingLists;
    }

    public void insert(ListeDeCoursesEntity listeDeCoursesEntity) {
        repository.insert(listeDeCoursesEntity);
    }

    public void delete(ListeDeCoursesEntity listeDeCoursesEntity) {
        repository.delete(listeDeCoursesEntity);
    }
}