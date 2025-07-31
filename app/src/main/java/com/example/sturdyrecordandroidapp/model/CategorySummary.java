package com.example.sturdyrecordandroidapp.model;

public class CategorySummary {
    public String category;
    public int total;

    public CategorySummary(String category, int total) {
        this.category = category;
        this.total = total;
    }

    public String getFormattedTime() {
        int hours = total / 60;
        int minutes = total % 60;
        return hours + "時間" + minutes + "分";
    }
}
