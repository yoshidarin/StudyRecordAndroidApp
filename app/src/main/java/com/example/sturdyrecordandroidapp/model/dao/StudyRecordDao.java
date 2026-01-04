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

    // 月別のカテゴリごと合計時間
    @Query("SELECT COALESCE(c.name, '未設定') as categoryName, SUM(duration_minutes) as totalTime " +
            "FROM study_records sr " +
            "LEFT JOIN categories c ON sr.category_id = c.id " +
            "WHERE strftime('%Y-%m', date) = :targetMonth " +
            "GROUP BY category_id")
    LiveData<List<CategorySummary>> getMonthlySummary(String targetMonth);

    // 月別の合計時間
    @Query("SELECT SUM(duration_minutes) " +
            "FROM study_records " +
            "WHERE strftime('%Y-%m', date) = :targetMonth")
    LiveData<Integer> getMonthlyTotalTime(String targetMonth);

    // 月別のレコード数
    @Query("SELECT COUNT(*) FROM study_records WHERE strftime('%Y-%m', date) = :targetMonth")
    LiveData<Integer> getMonthlyRecordCount(String targetMonth);
}
