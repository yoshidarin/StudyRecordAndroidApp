package com.example.sturdyrecordandroidapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sturdyrecordandroidapp.R;

public class MonthPickerDialogFragment extends DialogFragment {

    private OnMonthSelectedListener listener;
    private int initialYear;
    private int initialMonth;

    public interface OnMonthSelectedListener {
        void onMonthSelected(int year, int month);
    }

    public static MonthPickerDialogFragment newInstance(int year, int month, OnMonthSelectedListener listener) {
        MonthPickerDialogFragment fragment = new MonthPickerDialogFragment();
        fragment.initialYear = year;
        fragment.initialMonth = month;
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_month_picker, null);

        // NumberPickerの設定
        NumberPicker yearPicker = view.findViewById(R.id.yearPicker);
        NumberPicker monthPicker = view.findViewById(R.id.monthPicker);

        // 年の範囲設定（2000年～2100年）
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2100);
        yearPicker.setValue(initialYear);
        yearPicker.setWrapSelectorWheel(false);

        // 月の範囲設定（1月～12月）
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(initialMonth);
        monthPicker.setWrapSelectorWheel(true);

        builder.setView(view)
                .setTitle("月を選択")
                .setPositiveButton("決定", (dialog, which) -> {
                    if (listener != null) {
                        listener.onMonthSelected(yearPicker.getValue(), monthPicker.getValue());
                    }
                })
                .setNegativeButton("キャンセル", null);

        return builder.create();
    }
}