package com.example.sturdyrecordandroidapp.repository;

import androidx.lifecycle.LiveData;

import com.example.sturdyrecordandroidapp.db.AppDatabase;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.dao.StudyRecordDao;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StudyRecordRepository {
    private final StudyRecordDao studyRecordDao;

    @Inject
    public StudyRecordRepository(StudyRecordDao studyRecordDao) {
        this.studyRecordDao = studyRecordDao;
    }

    public void insert(StudyRecord record) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            studyRecordDao.insert(record);
        });
    }

    public LiveData<List<CategorySummary>> getMonthlySummary(String month) {
        return studyRecordDao.getMonthlySummary(month);
    }

    public LiveData<Integer> getMonthlyTotalTime(String month) {
        return studyRecordDao.getMonthlyTotalTime(month);
    }

    public LiveData<Integer> getMonthlyRecordCount(String targetMonth) {
        return studyRecordDao.getMonthlyRecordCount(targetMonth);
    }
}
