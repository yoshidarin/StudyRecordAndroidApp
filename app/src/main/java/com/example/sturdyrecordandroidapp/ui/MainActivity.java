package com.example.sturdyrecordandroidapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.db.AppDatabase;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.dao.CategoryDao;
import com.example.sturdyrecordandroidapp.model.dao.StudyRecordDao;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;
import com.example.sturdyrecordandroidapp.viewmodel.CategorySummaryViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity{

    private CategorySummaryViewModel viewModel;
    private CategorySummaryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.category_study_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppDatabase db = AppDatabase.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(CategorySummaryViewModel.class);

        // 両方のLiveDataが揃ったらAdapterにセット
        MediatorLiveData<Boolean> ready = (MediatorLiveData<Boolean>) viewModel.isReady();
        ready.observe(this, isReady -> {
            if (Boolean.TRUE.equals(isReady)) {
                List<CategorySummary> summaries = viewModel.getSummaryList().getValue();
                Map<Integer, String> map = viewModel.getCategoryIdToNameMap().getValue();
                if (summaries != null && map != null) {
                    CategorySummaryAdapter adapter = new CategorySummaryAdapter(summaries, map);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            CategoryDao categoryDao = db.categoryDao();
            StudyRecordDao studyRecordDao = db.studyRecordDao();

            String categoryName = "数学";
            Category existing = categoryDao.getCategoryByName(categoryName);
            if (existing == null) {
                Category newCat = new Category(categoryName);
                long id = categoryDao.insert(newCat);
                studyRecordDao.insert(new StudyRecord((int) id, "2025-08-01", 60));
                Log.d("InsertCheck", "inserted new category and record");
            } else {
                Log.d("InsertCheck", "category already exists");
            }
        });
    }
}
