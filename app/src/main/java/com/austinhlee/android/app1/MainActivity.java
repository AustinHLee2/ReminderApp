package com.austinhlee.android.app1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private TaskViewModel mTaskViewModel;

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        final TaskListAdapter adapter = new TaskListAdapter(this, mTaskViewModel);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(mContext, SecondActivity.class);
                startActivityForResult(intent,NEW_TASK_ACTIVITY_REQUEST_CODE);
                return true;

            /*case R.id.action_sort:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.sort_by_alphabetical:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        return firstTask.getTaskName().compareTo(secondTask.getTaskName());
                    }
                });
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_create:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        return firstTask.getCreationDate().compareTo(secondTask.getCreationDate());
                    }
                });
                mAdapter.notifyDataSetChanged();

                return true;

            case R.id.sort_by_due:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        if (firstTask.getDueDate() == null) {
                            return (secondTask.getDueDate() == null) ? 0 : -1;
                        }
                        if (secondTask.getDueDate() == null) {
                            return 1;
                        }
                        return secondTask.getDueDate().compareTo(firstTask.getDueDate());
                    }
                });
                mAdapter.notifyDataSetChanged();
                return true;*/

            case R.id.compact_view:
                return true;

            case R.id.detailed_view:
                return true;

            case R.id.action_edit:
                return true;

            case R.id.action_options:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Calendar cal = Calendar.getInstance();
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task();
            String taskTitle = data.getStringExtra(SecondActivity.EXTRA_TASK_NAME);
            task.setTaskName(taskTitle);
            task.setCreationDate((Date)data.getSerializableExtra(SecondActivity.EXTRA_TASK_CREATION_DATE));
            if (data.hasExtra(SecondActivity.EXTRA_TASK_DUE_DATE)){
                Date dueDate = (Date)data.getSerializableExtra(SecondActivity.EXTRA_TASK_DUE_DATE);
                task.setDueDate(dueDate);
                if (data.getBooleanExtra(SecondActivity.EXTRA_TASK_NOTIFACTION_SET, false)){
                    cal.setTime(dueDate);
                    NotificationScheduler.setReminder(mContext, AlarmReceiver.class, dueDate, taskTitle, task.getId());
                }
            }
            mTaskViewModel.insert(task);
        } else {
            Toast.makeText(mContext, "Must enter a task name!", Toast.LENGTH_LONG).show();
        }
    }
}
