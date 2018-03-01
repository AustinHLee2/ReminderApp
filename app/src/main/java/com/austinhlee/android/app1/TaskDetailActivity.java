package com.austinhlee.android.app1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTitleTextView;
    private TextView mDueDateTextView;
    private TextView mAdditionalNotesTextView;

    private TaskViewModel mTaskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mContext = this;
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTitleTextView = findViewById(R.id.detail_taskTitle);
        mDueDateTextView = findViewById(R.id.detail_dueDate);
        mAdditionalNotesTextView = findViewById(R.id.detail_additional_notes);

        Intent intent = getIntent();
        Task current = mTaskViewModel.getTaskByID(intent.getIntExtra(TaskListAdapter.EXTRA_TASK_ID,0));


        mTitleTextView.setText(current.getTaskName());
        if (current.getAdditionalNotes().equals("")) {
            mAdditionalNotesTextView.setVisibility(View.GONE);
        } else {
            mAdditionalNotesTextView.setText(current.getAdditionalNotes());
        }

        if (current.getDueDate() != null){
            mDueDateTextView.setText(formatDueDate(current.getDueDate()));
        } else {
            mDueDateTextView.setVisibility(View.GONE);
        }

    }

    private String formatDueDate(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm, dd MMM yy");
        return df.format(date);
    }
}