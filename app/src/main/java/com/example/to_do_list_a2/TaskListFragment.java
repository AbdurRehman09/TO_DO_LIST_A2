package com.example.to_do_list_a2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment implements TaskAdapter.TaskCompletionListener {

    private List<Task> tasks;
    private TaskAdapter adapter;
    private OnTaskSelectedListener taskSelectedListener;

    public interface OnTaskSelectedListener {
        void onTaskSelected(Task task);
    }

    public static TaskListFragment newInstance(List<Task> tasks) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("tasks", new ArrayList<>(tasks));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tasks = getArguments().getParcelableArrayList("tasks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        ListView listView = view.findViewById(R.id.task_list);
        adapter = new TaskAdapter(getContext(), tasks, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (taskSelectedListener != null) {
                taskSelectedListener.onTaskSelected(tasks.get(position));

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskSelectedListener) {
            taskSelectedListener = (OnTaskSelectedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnTaskSelectedListener");
        }
    }

    @Override
    public void onTaskCompletionChanged(Task task, boolean isCompleted) {
        ((MainActivity) getActivity()).onTaskCompletionChanged(task, isCompleted);
    }

    public void updateTasks(List<Task> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        adapter.notifyDataSetChanged();
    }
}
