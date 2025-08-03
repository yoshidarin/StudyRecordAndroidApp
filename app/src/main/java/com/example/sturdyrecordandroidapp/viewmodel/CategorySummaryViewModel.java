package com.example.sturdyrecordandroidapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.repository.StudyRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dagger.hilt.android.lifecycle.HiltViewModel;
import jakarta.inject.Inject;

@HiltViewModel
public class CategorySummaryViewModel extends AndroidViewModel {
    private final StudyRepository repository;
    private final LiveData<List<CategorySummary>> summaryList;
    private final LiveData<List<Category>> categoryList;
    private final MediatorLiveData<Boolean> ready = new MediatorLiveData<>();
    private final MediatorLiveData<Map<Integer, String>> categoryIdToNameMap = new MediatorLiveData<>();
    @Inject
    public CategorySummaryViewModel(@NonNull Application application) {
        super(application);
        repository = new StudyRepository(application);
        summaryList = repository.getMonthlySummary(getCurrentMonth());
        categoryList = repository.getAllCategories();
        ready.setValue(false);

        ready.addSource(categoryList, categories -> {
            Map<Integer, String> map = new HashMap<>();
            if (categories != null) {
                for (Category category : categories) {
                    map.put(category.id, category.name);
                }
                categoryIdToNameMap.setValue(map);
            }
            checkReady();
        });
        ready.addSource(summaryList, summaries -> {
            checkReady();
        });

    }

    public LiveData<List<CategorySummary>> getSummaryList() {
        return summaryList;
    }

    public LiveData<Boolean> isReady() {
        return ready;
    }

    public LiveData<Map<Integer, String>> getCategoryIdToNameMap() {
        return categoryIdToNameMap;
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    private String getCurrentMonth() {
        return new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
    }

    private void checkReady() {
        if (categoryList.getValue() != null && summaryList.getValue() != null) {
            ready.setValue(true);
        }
    }
}
