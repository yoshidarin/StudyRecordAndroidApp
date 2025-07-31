package com.example.sturdyrecordandroidapp.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_records")
public class StudyRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category")
    public String category; // 将来はCategoryエンティティに変更可

    @ColumnInfo(name = "date")
    public String date; // "yyyy-MM-dd"

    @ColumnInfo(name = "duration_minutes")
    public int durationMinutes;

    public StudyRecord(String category, String date, int durationMinutes) {
        this.category = category;
        this.date = date;
        this.durationMinutes = durationMinutes;
    }
}

