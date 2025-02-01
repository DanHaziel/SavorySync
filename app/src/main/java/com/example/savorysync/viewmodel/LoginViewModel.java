package com.example.savorysync.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private LiveData<List<LoginEntity>> getAllData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new LoginRepository(application);
        getAllData = repository.getAllData();

    }

    public void insert(LoginEntity data) {
        repository.insertData(data);
    }

    public LiveData<List<LoginEntity>> getGetAllData() {
        return getAllData;
    }

}
