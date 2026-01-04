package com.example.sturdyrecordandroidapp.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
