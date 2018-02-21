package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTitleTextView;
    private TextView mDueDateTextView;
    private TextView mCreationDateTextView;

    public TaskDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mContext = this;

        Intent intent = getIntent();

        mTitleTextView = findViewById(R.id.detail_taskTitle);
        mTitleTextView.setText(intent.getStringExtra("taskName"));

        mDueDateTextView = findViewById(R.id.detail_dueDate);
        mDueDateTextView.setText("No Due Date Set");
        if (intent.hasExtra("taskDueDate")) {
            Date dueDate = (Date) intent.getSerializableExtra("taskDueDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MM/dd/yy, HH:mm");
            String dueDateString = dateFormat.format(dueDate);
            mDueDateTextView.setText(dueDateString);
        }
        Date creationDate = (Date)intent.getSerializableExtra("taskCreationDate");

        SimpleDateFormat localDateFormat = new SimpleDateFormat("MM/dd/yy");
        String creationDateString = localDateFormat.format(creationDate);

        mCreationDateTextView = findViewById(R.id.detail_creationDate);
        mCreationDateTextView.setText(creationDateString);

    }
}
