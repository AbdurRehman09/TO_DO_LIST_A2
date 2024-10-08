package com.example.to_do_list_a2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskSelectedListener, TaskInputFragment.OnTaskAddedListener, TaskAdapter.TaskCompletionListener {

    private List<Task> todoTasks;
    private List<Task> completedTasks;
    private TaskListFragment todoListFragment;
    private TaskListFragment completedListFragment;
    private TaskDetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            todoTasks = new ArrayList<>();
            completedTasks = new ArrayList<>();
        } else {
            todoTasks = savedInstanceState.getParcelableArrayList("todoTasks");
            completedTasks = savedInstanceState.getParcelableArrayList("completedTasks");
        }

        setupFragments();

        FloatingActionButton fabAddTask = findViewById(R.id.fab_add_task);
        fabAddTask.setOnClickListener(v -> openTaskInputFragment());
    }

    private void setupFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        todoListFragment = (TaskListFragment) fragmentManager.findFragmentById(R.id.todo_list_container);

        if (todoListFragment == null) {
            todoListFragment = TaskListFragment.newInstance(todoTasks);
            fragmentManager.beginTransaction()
                    .add(R.id.todo_list_container, todoListFragment)
                    .commit();
        }

        if (findViewById(R.id.task_details_container) != null) {
            // We're in landscape mode
            detailsFragment = (TaskDetailsFragment) fragmentManager.findFragmentById(R.id.task_details_container);
            if (detailsFragment == null && !todoTasks.isEmpty()) {
                showTaskDetails(todoTasks.get(0));
            }
        }
    }

    @Override
    public void onTaskSelected(Task task) {
        showTaskDetails(task);
    }

    private void showTaskDetails(Task task) {
        detailsFragment = TaskDetailsFragment.newInstance(task.getName(), task.getDescription());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_details_container, detailsFragment)
                .commit();
    }

    @Override
    public void onTaskAdded(String name, String description) {
        Task newTask = new Task(name, description);
        todoTasks.add(newTask);
        todoListFragment.updateTasks(todoTasks);
    }

    @Override
    public void onTaskCompletionChanged(Task task, boolean isCompleted) {
        if (isCompleted) {
            todoTasks.remove(task);
            completedTasks.add(task);
        } else {
            completedTasks.remove(task);
            todoTasks.add(task);
        }
        task.setCompleted(isCompleted);
        todoListFragment.updateTasks(todoTasks);
        if (completedListFragment != null) {
            completedListFragment.updateTasks(completedTasks);
        }
    }

    private void openTaskInputFragment() {
        TaskInputFragment taskInputFragment = new TaskInputFragment();
        taskInputFragment.show(getSupportFragmentManager(), "taskInput");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_completed) {
            showCompletedTasks();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCompletedTasks() {
        completedListFragment = TaskListFragment.newInstance(completedTasks);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.todo_list_container, completedListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("todoTasks", new ArrayList<>(todoTasks));
        outState.putParcelableArrayList("completedTasks", new ArrayList<>(completedTasks));
    }
}