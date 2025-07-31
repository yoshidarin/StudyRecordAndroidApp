package com.example.sturdyrecordandroidapp.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.db.AppDatabase;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.category_study_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppDatabase db = AppDatabase.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new Thread(() -> {
            /* 仮データを挿入（初回のみなどで）
            db.studyRecordDao().insert(new StudyRecord("英語", "2025-07-25", 90));
            db.studyRecordDao().insert(new StudyRecord("数学", "2025-07-26", 60));
            db.studyRecordDao().insert(new StudyRecord("英語", "2025-07-27", 75));
             */

            // 今月のカテゴリ別合計時間を取得
            String thisMonth = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
            List<CategorySummary> summaryList = db.studyRecordDao().getMonthlySummary(thisMonth);

            // UIスレッドでRecyclerView更新
            runOnUiThread(() -> {
                CategorySummaryAdapter adapter = new CategorySummaryAdapter(summaryList);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}
