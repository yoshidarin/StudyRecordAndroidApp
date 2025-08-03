package com.example.sturdyrecordandroidapp.model;

public class CategorySummary {
    public int categoryId;
    public int total;

    public CategorySummary(int  categoryId, int total) {
        this.categoryId = categoryId;
        this.total = total;
    }

    public String getFormattedTime() {
        int hours = total / 60;
        int minutes = total % 60;
        return hours + "時間" + minutes + "分";
    }
}
