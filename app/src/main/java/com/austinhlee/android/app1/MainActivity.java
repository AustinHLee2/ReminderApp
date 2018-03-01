package com.austinhlee.android.app1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private Context mContext;
    private TaskViewModel mTaskViewModel;
    private TaskListAdapter mTaskListAdapter;
    private SharedPreferences mSharedPreferences;

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskListAdapter = new TaskListAdapter(this, mTaskViewModel);

        mSharedPreferences = mContext.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mTaskListAdapter.setTasks(tasks);
            }
        });

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        int viewType = mSharedPreferences.getInt(getString(R.string.list_default_view_key), -1);
        if (viewType == -1) {
            mTaskListAdapter.setCurrentViewType(mTaskListAdapter.COMPACT_VIEW_TYPE);
        }
        else {
            mTaskListAdapter.setCurrentViewType(viewType);
        }

        recyclerView.setAdapter(mTaskListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(getString(R.string.list_default_view_key), mTaskListAdapter.getItemViewType(0));
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(mContext, SecondActivity.class);
                startActivityForResult(intent,NEW_TASK_ACTIVITY_REQUEST_CODE);
                return true;

            case R.id.compact_view:
                mTaskListAdapter.setCurrentViewType(0);
                return true;

            case R.id.detailed_view:
                mTaskListAdapter.setCurrentViewType(1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position){
        if (viewHolder instanceof TaskListAdapter.TaskViewHolder){
            mTaskViewModel.deleteTask(mTaskViewModel.getAllTasks().getValue().get(position).getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Calendar cal = Calendar.getInstance();
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task();
            String taskTitle = data.getStringExtra(SecondActivity.EXTRA_TASK_NAME);
            String additionalNotes = data.getStringExtra(SecondActivity.EXTRA_ADDITIONAL_NOTES);
            task.setTaskName(taskTitle);
            task.setAdditionalNotes(additionalNotes);
            if (data.hasExtra(SecondActivity.EXTRA_TASK_DUE_DATE)) {
                Date dueDate = (Date) data.getSerializableExtra(SecondActivity.EXTRA_TASK_DUE_DATE);
                task.setDueDate(dueDate);
                if (data.getBooleanExtra(SecondActivity.EXTRA_TASK_NOTIFACTION_SET, false)) {
                    cal.setTime(dueDate);
                    task.setHasNotification(true);
                    NotificationScheduler.setReminder(mContext, AlarmReceiver.class, dueDate, taskTitle, task.getId());
                } else {
                    task.setHasNotification(false);
                }
            }
            mTaskViewModel.insert(task);
        } else {
            Toast.makeText(mContext, "Must enter a task name!", Toast.LENGTH_LONG).show();
        }
    }
}
