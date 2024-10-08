package com.example.to_do_list_a2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private TaskCompletionListener completionListener;

    public interface TaskCompletionListener {
        void onTaskCompletionChanged(Task task, boolean isCompleted);
    }

    public TaskAdapter(Context context, List<Task> tasks, TaskCompletionListener listener) {
        super(context, 0, tasks);
        this.completionListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        TextView taskName = convertView.findViewById(R.id.task_name);
        CheckBox taskCompleted = convertView.findViewById(R.id.task_completed);

        taskName.setText(task.getName());
        taskCompleted.setChecked(task.isCompleted());

        taskCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (completionListener != null) {
                completionListener.onTaskCompletionChanged(task, isChecked);
            }
        });

        return convertView;
    }
}