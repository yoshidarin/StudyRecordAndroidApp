package com.example.sturdyrecordandroidapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.CategorySummary;
import com.example.sturdyrecordandroidapp.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainFragment extends Fragment {

    private MainViewModel viewModel;
    private TextView tvCurrentMonth;
    private TextView tvTotalTime;
    private RecyclerView rvCategorySummary;
    private Button btnAddRecord;

    private CategorySummaryAdapter adapter;
    private String selectedMonth;
    private Observer<Integer> totalObserver;
    private Observer<List<CategorySummary>> summaryObserver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelの取得
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Viewの初期化
        tvCurrentMonth = view.findViewById(R.id.tvCurrentMonth);
        tvTotalTime = view.findViewById(R.id.tvTotalTime);
        rvCategorySummary = view.findViewById(R.id.rvCategorySummary);
        btnAddRecord = view.findViewById(R.id.btnAddRecord);

        // 初期表示は当月
        selectedMonth = viewModel.getCurrentMonth();
        updateMonthDisplay();

        // Observerを作成
        createObservers();

        // RecyclerViewのセットアップ
        adapter = new CategorySummaryAdapter();
        rvCategorySummary.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCategorySummary.setAdapter(adapter);

        // 月表示をクリックすると月選択ダイアログを表示
        tvCurrentMonth.setOnClickListener(v -> showMonthPickerDialog());

        // 初回データ取得
        observeMonthData(selectedMonth);

        // 記録追加ボタン
        btnAddRecord.setOnClickListener(v -> {
            RecordDialogFragment dialog = new RecordDialogFragment();
            dialog.show(getParentFragmentManager(), "RecordDialog");
        });
    }
    private void createObservers() {
        // 合計時間のObserver
        totalObserver = totalMinutes -> {
            if (totalMinutes != null) {
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                tvTotalTime.setText(String.format("%02d時間%02d分", hours, minutes));
            } else {
                tvTotalTime.setText("00時間00分");
            }
        };

        // カテゴリ別集計のObserver
        summaryObserver = summaries -> {
            if (summaries != null && !summaries.isEmpty()) {
                adapter.setSummaryList(summaries);
            } else {
                adapter.setSummaryList(null);
            }
        };
    }

    private void observeMonthData(String month) {
        // 古い監視を削除
        viewModel.getMonthlyTotalTime(month).removeObserver(totalObserver);
        viewModel.getMonthlyCategorySummary(month).removeObserver(summaryObserver);

        // 新しい月のデータを監視
        viewModel.getMonthlyTotalTime(month).observe(getViewLifecycleOwner(), totalObserver);
        viewModel.getMonthlyCategorySummary(month).observe(getViewLifecycleOwner(), summaryObserver);
    }


    private void showMonthPickerDialog() {
        Calendar calendar = Calendar.getInstance();

        // 現在選択されている月を初期値にする
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            calendar.setTime(sdf.parse(selectedMonth));
        } catch (Exception e) {
            // パースエラーの場合は現在の月を使用
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // Calendarは0始まりなので+1

        MonthPickerDialogFragment dialog = MonthPickerDialogFragment.newInstance(
                year,
                month,
                (selectedYear, selectedMonth) -> {
                    this.selectedMonth = String.format(Locale.getDefault(), "%04d-%02d", selectedYear, selectedMonth);
                    updateMonthDisplay();

                    // データを再取得
                    observeMonthData(this.selectedMonth);
                }
        );

        dialog.show(getParentFragmentManager(), "MonthPicker");
    }

    private void updateMonthDisplay() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月", Locale.JAPANESE);
            String displayMonth = outputFormat.format(inputFormat.parse(selectedMonth));
            tvCurrentMonth.setText(displayMonth);
        } catch (Exception e) {
            tvCurrentMonth.setText(selectedMonth);
        }
    }
}