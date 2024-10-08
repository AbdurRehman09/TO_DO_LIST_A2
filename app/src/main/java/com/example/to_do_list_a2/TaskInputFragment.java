package com.example.to_do_list_a2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TaskInputFragment extends DialogFragment {

    private EditText taskNameInput;
    private EditText taskDescriptionInput;
    private OnTaskAddedListener listener;

    public interface OnTaskAddedListener {
        void onTaskAdded(String name, String description);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskAddedListener) {
            listener = (OnTaskAddedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnTaskAddedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_input, container, false);

        taskNameInput = view.findViewById(R.id.task_name_input);
        taskDescriptionInput = view.findViewById(R.id.task_description_input);
        Button saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> saveTask());

        return view;
    }

    private void saveTask() {
        String name = taskNameInput.getText().toString().trim();
        String description = taskDescriptionInput.getText().toString().trim();

        if (!name.isEmpty()) {
            listener.onTaskAdded(name, description);
            dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }
}