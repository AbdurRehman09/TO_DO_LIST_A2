package com.example.to_do_list_a2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskDetailsFragment extends Fragment {

    private static final String ARG_TASK_NAME = "task_name";
    private static final String ARG_TASK_DESCRIPTION = "task_description";

    public static TaskDetailsFragment newInstance(String taskName, String taskDescription) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_NAME, taskName);
        args.putString(ARG_TASK_DESCRIPTION, taskDescription);
        fragment.setArguments(args);
        return fragment;
    }
    public void updateTaskDetails(String taskName, String taskDescription) {
        TextView taskNameTextView = getView().findViewById(R.id.task_name);
        TextView taskDescriptionTextView = getView().findViewById(R.id.task_description);

        taskNameTextView.setText(taskName);
        taskDescriptionTextView.setText(taskDescription);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        TextView taskNameTextView = view.findViewById(R.id.task_name);
        TextView taskDescriptionTextView = view.findViewById(R.id.task_description);

        if (getArguments() != null) {
            String taskName = getArguments().getString(ARG_TASK_NAME);
            String taskDescription = getArguments().getString(ARG_TASK_DESCRIPTION);

            taskNameTextView.setText(taskName);
            taskDescriptionTextView.setText(taskDescription);
        }

        return view;
    }
}