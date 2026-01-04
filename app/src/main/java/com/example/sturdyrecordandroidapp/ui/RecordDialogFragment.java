package com.example.sturdyrecordandroidapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.model.entity.StudyRecord;
import com.example.sturdyrecordandroidapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecordDialogFragment extends DialogFragment {
    private MainViewModel viewModel;
    private Spinner spinnerCategory;
    private EditText etDurationMinutes;
    private List<Category> categoryList = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_record, null);

        // ViewModelの取得
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Viewの初期化
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etDurationMinutes = view.findViewById(R.id.etDurationMinutes);

        // カテゴリリストの設定
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // カテゴリを監視
        viewModel.getAllCategories().observe(this, categories -> {
            if (categories != null && !categories.isEmpty()) {
                categoryList.clear();
                categoryList.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setView(view)
                .setTitle("勉強記録を追加")
                .setPositiveButton("保存", null) // nullにして後で設定
                .setNegativeButton("キャンセル", (dialog, which) -> dismiss());

        AlertDialog dialog = builder.create();

        // ダイアログ表示後にボタンの動作を設定（入力チェックのため）
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (validateAndSave()) {
                    dismiss();
                }
            });
        });

        return dialog;
    }

    private boolean validateAndSave() {
        // カテゴリの確認
        if (categoryList.isEmpty()) {
            Toast.makeText(requireContext(), "カテゴリを先に作成してください", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 時間の確認
        String durationStr = etDurationMinutes.getText().toString().trim();
        if (durationStr.isEmpty()) {
            Toast.makeText(requireContext(), "勉強時間を入力してください", Toast.LENGTH_SHORT).show();
            return false;
        }

        int durationMinutes;
        try {
            durationMinutes = Integer.parseInt(durationStr);
            if (durationMinutes <= 0) {
                Toast.makeText(requireContext(), "1分以上を入力してください", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "正しい数値を入力してください", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 記録を保存
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        String todayDate = viewModel.getTodayDate();

        StudyRecord record = new StudyRecord(selectedCategory.id, todayDate, durationMinutes);
        viewModel.insertRecord(record);

        Toast.makeText(requireContext(), "記録を保存しました", Toast.LENGTH_SHORT).show();
        return true;
    }
}
