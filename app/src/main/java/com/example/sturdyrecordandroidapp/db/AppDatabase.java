package com.example.sturdyrecordandroidapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sturdyrecordandroidapp.model.dao.CategoryDao;
import com.example.sturdyrecordandroidapp.model.dao.StudyRecordDao;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;

@Database(entities = {StudyRecord.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract StudyRecordDao studyRecordDao();
    public abstract CategoryDao categoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "study_database")
                    .fallbackToDestructiveMigration() // 開発中のみ：スキーマ変更時に自動リセット
                    .build();
        }
        return INSTANCE;
    }
}
