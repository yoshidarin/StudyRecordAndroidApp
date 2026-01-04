package com.example.sturdyrecordandroidapp.model;

public class CategorySummary {
    public String categoryName;
    public int totalTime;

    public CategorySummary(String categoryName, int totalTime) {
        this.categoryName = categoryName;
        this.totalTime = totalTime;
    }

    public String getFormattedTime() {
        int hours = totalTime / 60;
        int minutes = totalTime % 60;
        return String.format("%d時間%d分", hours, minutes);
    }
}
