package com.example.sturdyrecordandroidapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import java.util.ArrayList;
import java.util.List;

public class CategorySummaryAdapter extends RecyclerView.Adapter<CategorySummaryAdapter.ViewHolder> {

    private List<CategorySummary> summaryList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategorySummary summary = summaryList.get(position);
        holder.bind(summary);
    }

    @Override
    public int getItemCount() {
        return summaryList != null ? summaryList.size() : 0;
    }

    public void setSummaryList(List<CategorySummary> summaryList) {
        if (summaryList != null) {
            this.summaryList = summaryList;
        } else {
            this.summaryList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        TextView tvCategoryTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryTime = itemView.findViewById(R.id.tvCategoryTime);
        }
        public void bind(CategorySummary summary) {
            if (summary != null) {
                tvCategoryName.setText(summary.categoryName);
                tvCategoryTime.setText(summary.getFormattedTime());
            }
        }
    }
}
