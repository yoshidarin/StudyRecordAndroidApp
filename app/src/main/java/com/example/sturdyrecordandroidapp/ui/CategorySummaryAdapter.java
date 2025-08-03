package com.example.sturdyrecordandroidapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.dao.CategoryDao;
import com.example.sturdyrecordandroidapp.model.entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorySummaryAdapter extends RecyclerView.Adapter<CategorySummaryAdapter.ViewHolder> {

    private List<CategorySummary> categorySummaries;
    private Map<Integer, String> categoryIdToName = new HashMap<>();

    public void setData(List<CategorySummary> summaries, List<Category> categories) {
        this.categorySummaries = summaries;
        categoryIdToName.clear();
        for (Category c : categories) {
            categoryIdToName.put(c.id, c.name);
        }
        notifyDataSetChanged();
    }

    public CategorySummaryAdapter(List<CategorySummary> categorySummaries, Map<Integer, String> categoryIdToName) {
        this.categorySummaries = categorySummaries;
        this.categoryIdToName = categoryIdToName;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCategoryName;
        TextView textCategoryTime;

        public ViewHolder(View itemView) {
            super(itemView);
            textCategoryName = itemView.findViewById(R.id.textCategoryName);
            textCategoryTime = itemView.findViewById(R.id.textCategoryTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategorySummary summary = categorySummaries.get(position);
        holder.textCategoryName.setText(categoryIdToName.getOrDefault(summary.categoryId, "不明"));
        holder.textCategoryTime.setText(summary.getFormattedTime());
    }
        /*
        int hours = summary.total / 60;
        int minutes = summary.total % 60;
        holder.textCategoryTime.setText(hours + "時間" + minutes + "分");
         */

    @Override
    public int getItemCount() {
        return categorySummaries.size();
    }
}
