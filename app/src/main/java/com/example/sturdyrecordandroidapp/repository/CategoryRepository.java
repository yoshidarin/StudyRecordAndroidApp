package com.example.sturdyrecordandroidapp.repository;

import androidx.lifecycle.LiveData;

import com.example.sturdyrecordandroidapp.db.AppDatabase;
import com.example.sturdyrecordandroidapp.model.dao.CategoryDao;
import com.example.sturdyrecordandroidapp.model.entity.Category;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CategoryRepository {
    private final CategoryDao categoryDao;

    @Inject
    public CategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    public void insert(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.insert(category);
        });
    }

    public void update(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.update(category);
        });
    }

    public void delete(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.delete(category);
        });
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.deleteById(id);
        });
    }
}
