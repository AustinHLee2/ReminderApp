package com.austinhlee.android.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity{

    private Button mTimeButton;
    private EditText mEditText;
    private Task mTask;
    private TimePickerFragment mTimePickerFragment;
    private DatePickerFragment mDatePickerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mTimePickerFragment = new TimePickerFragment();

        mDatePickerFragment = new DatePickerFragment();

        mEditText = (EditText) findViewById(R.id.taskNameEditText);

        mTask = new Task("_");

        mTimeButton = (Button) findViewById(R.id.pickTimeButton);
    }

    public void showTimePickerDialog(View v) {
        mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        mDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                mTask.setTaskName(mEditText.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.set(mDatePickerFragment.getYear(), mDatePickerFragment.getMonth(), mDatePickerFragment.getDay());
                Date date = calendar.getTime();
                mTask.setDueDate(date);
                Database.get(getApplicationContext()).addTask(mTask);
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
