package com.example.savorysync.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.savorysync.model.dao.LoginDao;
import com.example.savorysync.model.entity.LoginEntity;
import com.example.savorysync.model.AppDatabase;

import java.util.List;

public class LoginRepository {

    private LoginDao loginDao;
    private LiveData<List<LoginEntity>> allData;

    public LoginRepository(Application application) {

        AppDatabase db = AppDatabase.getDatabaseFromJava(application);
        loginDao = db.loginDao();
        allData = loginDao.getDetails();

    }

    public LiveData<List<LoginEntity>> getAllData() {
        return allData;
    }

    public void insertData(LoginEntity loginEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> loginDao.insertDetails(loginEntity));
    }

    public void delete(LoginEntity loginEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> loginDao.delete(loginEntity));
    }

}
