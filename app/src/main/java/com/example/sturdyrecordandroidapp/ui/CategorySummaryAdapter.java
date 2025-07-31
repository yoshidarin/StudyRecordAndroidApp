package com.example.sturdyrecordandroidapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.CategorySummary;

import java.util.List;

public class CategorySummaryAdapter extends RecyclerView.Adapter<CategorySummaryAdapter.ViewHolder> {

    private List<CategorySummary> categorySummaries;

    public CategorySummaryAdapter(List<CategorySummary> categorySummaries) {
        this.categorySummaries = categorySummaries;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategorySummary summary = categorySummaries.get(position);
        holder.textCategoryName.setText(summary.category);
        int hours = summary.total / 60;
        int minutes = summary.total % 60;
        holder.textCategoryTime.setText(hours + "時間" + minutes + "分");
    }

    @Override
    public int getItemCount() {
        return categorySummaries.size();
    }
}
