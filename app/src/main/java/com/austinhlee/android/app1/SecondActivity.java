package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SecondActivity extends AppCompatActivity{

    private Button mTimeButton;
    private EditText mEditText;
    private Context mContext;
    private TimePickerFragment mTimePickerFragment;
    private DatePickerFragment mDatePickerFragment;
    private TextView mTimePreview;
    private TextView mDatePreview;
    private CheckBox mCheckBox;
    private CheckBox mNotificationCheckBox;
    private Boolean mTaskNameHasInput;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = this;
        mTaskNameHasInput = false;

        mTimeButton = (Button) findViewById(R.id.pickTimeButton);

        mTimePreview = (TextView) findViewById(R.id.time_preview);

        mDatePreview = (TextView) findViewById(R.id.date_preview);

        mLinearLayout = (LinearLayout)findViewById(R.id.setDueDateLinearLayout);
        mTimePickerFragment = new TimePickerFragment();

        mNotificationCheckBox = (CheckBox)findViewById(R.id.notificationCheckBox);

        mDatePickerFragment = new DatePickerFragment();

        mCheckBox = (CheckBox)findViewById(R.id.setDueDateCheckBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                       mLinearLayout.setVisibility(View.VISIBLE);
                       mNotificationCheckBox.setChecked(false);
                       if (mDatePreview.getVisibility() == View.VISIBLE){
                           mDatePreview.setVisibility(View.INVISIBLE);
                           mTimePreview.setVisibility(View.INVISIBLE);
                       }
                    }
                    else {
                        mLinearLayout.setVisibility(View.INVISIBLE);
                        mNotificationCheckBox.setVisibility(View.INVISIBLE);
                    }
            }
        });

        mEditText = (EditText) findViewById(R.id.taskNameEditText);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")){
                        mTaskNameHasInput = false;
                    }
                    else {
                        mTaskNameHasInput = true;
                    }
                    invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (!mTaskNameHasInput) {
            menu.findItem(R.id.action_submit).setVisible(false);
            // You can also use something like:
            // menu.findItem(R.id.example_foobar).setEnabled(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                Intent intent = new Intent();
                intent.putExtra("taskName", mEditText.getText().toString());
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                intent.putExtra("creationDate", date);
                intent.putExtra("dueDateSet", false);
            if (mNotificationCheckBox.isChecked() && mDatePreview.getVisibility() == View.VISIBLE) {
                    calendar.set(mDatePickerFragment.getYear(), mDatePickerFragment.getMonth(), mDatePickerFragment.getDay(), mTimePickerFragment.getHour(), mTimePickerFragment.getMinute());
                    date = calendar.getTime();
                    intent.putExtra("dueDate", date);
                    intent.putExtra("setNotification", true);}
                setResult(RESULT_OK, intent);
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
