package com.example.sturdyrecordandroidapp.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "study_records",
        foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = ForeignKey.SET_NULL // カテゴリ削除時にnullになるように
))
public class StudyRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(index = true, name = "category_id")
    public Integer categoryId;

    @ColumnInfo(name = "date")
    public String date; // "yyyy-MM-dd"

    @ColumnInfo(name = "duration_minutes")
    public int durationMinutes;

    public StudyRecord(Integer categoryId, String date, int durationMinutes) {
        this.categoryId = categoryId;
        this.date = date;
        this.durationMinutes = durationMinutes;
    }
}

