package com.example.sturdyrecordandroidapp.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sturdyrecordandroidapp.R;
import com.example.sturdyrecordandroidapp.model.entity.Category;
import com.example.sturdyrecordandroidapp.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryEditFragment extends Fragment {

    private MainViewModel viewModel;
    private ListView lvCategories;
    private Button btnAddCategory;

    private ArrayAdapter<Category> adapter;
    private List<Category> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelの取得
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Viewの初期化
        lvCategories = view.findViewById(R.id.lvCategories);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);

        // ListViewのアダプター設定
        // android.R.layout.simple_list_item_1 は標準の1行テキストレイアウト
        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, categoryList);
        lvCategories.setAdapter(adapter);

        // カテゴリリストを監視
        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryList.clear();
                categoryList.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        });

        // アイテム長押しで編集・削除メニュー表示
        lvCategories.setOnItemLongClickListener((parent, view1, position, id) -> {
            Category category = categoryList.get(position);
            showEditDeleteDialog(category);
            return true;
        });

        // カテゴリ追加ボタン
        btnAddCategory.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_category, null);
        EditText etCategoryName = dialogView.findViewById(R.id.etCategoryName);

        builder.setView(dialogView)
                .setTitle("カテゴリを追加")
                .setPositiveButton("追加", (dialog, which) -> {
                    String name = etCategoryName.getText().toString().trim();
                    if (!name.isEmpty()) {
                        viewModel.insertCategory(new Category(name));
                        Toast.makeText(requireContext(), "追加しました", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "名前を入力してください", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

    private void showEditDeleteDialog(Category category) {
        String[] options = {"編集", "削除"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(category.name)
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // 編集
                        showEditCategoryDialog(category);
                    } else if (which == 1) {
                        // 削除確認
                        showDeleteConfirmDialog(category);
                    }
                })
                .show();
    }

    private void showEditCategoryDialog(Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_category, null);
        EditText etCategoryName = dialogView.findViewById(R.id.etCategoryName);
        etCategoryName.setText(category.name);

        builder.setView(dialogView)
                .setTitle("カテゴリを編集")
                .setPositiveButton("保存", (dialog, which) -> {
                    String name = etCategoryName.getText().toString().trim();
                    if (!name.isEmpty()) {
                        category.name = name;
                        viewModel.updateCategory(category);
                        Toast.makeText(requireContext(), "更新しました", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "名前を入力してください", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

    private void showDeleteConfirmDialog(Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("削除確認")
                .setMessage(category.name + " を削除しますか？\n関連する記録のカテゴリは未設定になります。")
                .setPositiveButton("削除", (dialog, which) -> {
                    viewModel.deleteCategory(category);
                    Toast.makeText(requireContext(), "削除しました", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }
}
