package com.example.sturdyrecordandroidapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;
import com.example.sturdyrecordandroidapp.repository.CategoryRepository;
import com.example.sturdyrecordandroidapp.repository.StudyRecordRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;
    private final StudyRecordRepository studyRecordRepository;

    @Inject
    public MainViewModel(CategoryRepository categoryRepository,
                         StudyRecordRepository studyRecordRepository) {
        this.categoryRepository = categoryRepository;
        this.studyRecordRepository = studyRecordRepository;
    }

    // === カテゴリ関連 ===

    public LiveData<List<Category>> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public void insertCategory(Category category) {
        categoryRepository.insert(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    // === 記録関連 ===

    public void insertRecord(StudyRecord record) {
        studyRecordRepository.insert(record);
    }

    // 当月の合計時間
    public LiveData<Integer> getCurrentMonthTotalTime() {
        String currentMonth = getCurrentMonth();
        return studyRecordRepository.getMonthlyTotalTime(currentMonth);
    }

    // 当月のカテゴリ別集計
    public LiveData<List<CategorySummary>> getCurrentMonthCategorySummary() {
        String currentMonth = getCurrentMonth();
        return studyRecordRepository.getMonthlySummary(currentMonth);
    }

    // 指定月の合計時間
    public LiveData<Integer> getMonthlyTotalTime(String targetMonth) {
        return studyRecordRepository.getMonthlyTotalTime(targetMonth);
    }

    // 指定月のカテゴリ別集計
    public LiveData<List<CategorySummary>> getMonthlyCategorySummary(String targetManth) {
        return studyRecordRepository.getMonthlySummary(targetManth);
    }

    // 当月の取得
    public String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    // 今日の日付を取得（例: "2025-10-26"）
    public String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }
}
