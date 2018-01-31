package com.austinhlee.android.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity{

    private Button mButton;
    private Button mTimeButton;
    private EditText mEditText;
    private Task mTask;
    private TimePickerFragment mTimePickerFragment;
    private DatePickerFragment mDatePickerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mEditText = (EditText) findViewById(R.id.taskNameEditText);

        mTask = new Task("_");

        mButton = (Button) findViewById(R.id.createButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.setTaskName(mEditText.getText().toString());
                mTask.setDueDate(formatDueDate(mDatePickerFragment.getMonth(),mDatePickerFragment.getDay(),mDatePickerFragment.getYear(),mTimePickerFragment.getHour(),mTimePickerFragment.getMinute()));
                Database.get(getApplicationContext()).addTask(mTask);
                finish();
            }
        });

        mTimeButton = (Button) findViewById(R.id.pickTimeButton);
    }

    public void showTimePickerDialog(View v) {
        mTimePickerFragment = new TimePickerFragment();
        mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        mDatePickerFragment = new DatePickerFragment();
        mDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String formatDueDate(int month, int day, int year, int hour, int minute){
        String formattedMinute;
        if (minute < 10){
            formattedMinute = "0" + minute;
        }
        else{
            formattedMinute = Integer.toString(minute);
        }
        return month + "/" + day + "/" + year + ", " + hour + ":" + formattedMinute;
    }
}
