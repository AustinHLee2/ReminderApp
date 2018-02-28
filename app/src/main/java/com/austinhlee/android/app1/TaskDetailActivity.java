package com.austinhlee.android.app1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTitleTextView;
    private TextView mDueDateTextView;
    private TextView mCreationDateTextView;

    private TaskViewModel mTaskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mContext = this;
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTitleTextView = findViewById(R.id.detail_taskTitle);
        mDueDateTextView = findViewById(R.id.detail_dueDate);
        mCreationDateTextView = findViewById(R.id.detail_creationDate);

        Intent intent = getIntent();
        Task current = mTaskViewModel.getTaskByID(intent.getIntExtra(TaskListAdapter.EXTRA_TASK_ID,0));

        mTitleTextView.setText(current.getTaskName());
        if (current.getDueDate() == null){
            mDueDateTextView.setText("No due date set");
        } else {
            mDueDateTextView.setText(current.getDueDate().toString());
        }
        mCreationDateTextView.setText(formatDueDate(current.getCreationDate()));

    }

    private String formatDueDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        return df.format(date);
    }
}