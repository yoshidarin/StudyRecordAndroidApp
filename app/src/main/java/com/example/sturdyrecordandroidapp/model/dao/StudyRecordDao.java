package com.example.sturdyrecordandroidapp.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;

import java.util.List;

@Dao
public interface  StudyRecordDao {
    @Insert
    void insert(StudyRecord record);

    @Query("SELECT * FROM study_records")
    List<StudyRecord> getAll();

    @Query("SELECT category_id as categoryId, SUM(duration_minutes) as total " +
            "FROM study_records " +
            "WHERE strftime('%Y-%m', date) = :targetMonth " +
            "GROUP BY category_id")
    LiveData<List<CategorySummary>> getMonthlySummary(String targetMonth);
}
