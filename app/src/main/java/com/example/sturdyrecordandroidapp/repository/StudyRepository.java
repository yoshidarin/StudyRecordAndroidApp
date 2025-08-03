package com.example.sturdyrecordandroidapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sturdyrecordandroidapp.db.AppDatabase;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.dao.CategoryDao;
import com.example.sturdyrecordandroidapp.model.dao.StudyRecordDao;
import com.example.sturdyrecordandroidapp.model.entity.Category;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class StudyRepository {
    private final CategoryDao categoryDao;
    private final StudyRecordDao studyRecordDao;

    @Inject
    public StudyRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.categoryDao = db.categoryDao();
        this.studyRecordDao = db.studyRecordDao();
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAll();
    }

    public LiveData<List<CategorySummary>> getMonthlySummary(String month) {
        return studyRecordDao.getMonthlySummary(month);
    }
}
