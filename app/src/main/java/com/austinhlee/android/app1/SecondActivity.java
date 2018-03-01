package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SecondActivity extends AppCompatActivity{

    private Button mTimeButton;
    private EditText mTaskNameEditText;
    private Context mContext;
    private TimePickerFragment mTimePickerFragment;
    private DatePickerFragment mDatePickerFragment;
    private TextView mTimePreview;
    private TextView mDatePreview;
    private CheckBox mSetDueDateCheckBox;
    private CheckBox mNotificationCheckBox;
    private LinearLayout mLinearLayout;
    private EditText mAdditionalNotesEditText;

    public static final String EXTRA_TASK_NAME = "com.austinhlee.android:app1.TASK_NAME";
    public static final String EXTRA_ADDITIONAL_NOTES = "com.austinhlee.android:app1.ADDITIONAL_NOTES";
    public static final String EXTRA_TASK_DUE_DATE = "com.austinhlee.android:app1.DUE_DATE";
    public static final String EXTRA_TASK_NOTIFACTION_SET = "com.austinhlee.android:app1.NOTIFICATION_SET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = this;

        mAdditionalNotesEditText = (EditText) findViewById(R.id.extraNotesEditText);

        mTimeButton = (Button) findViewById(R.id.pickTimeButton);

        mTimePreview = (TextView) findViewById(R.id.time_preview);

        mDatePreview = (TextView) findViewById(R.id.date_preview);

        mLinearLayout = (LinearLayout)findViewById(R.id.setDueDateLinearLayout);
        mTimePickerFragment = new TimePickerFragment();

        mNotificationCheckBox = (CheckBox)findViewById(R.id.notificationCheckBox);

        mDatePickerFragment = new DatePickerFragment();

        mSetDueDateCheckBox = (CheckBox)findViewById(R.id.setDueDateCheckBox);
        mSetDueDateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    TransitionManager.beginDelayedTransition(mLinearLayout);
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

        mTaskNameEditText = (EditText) findViewById(R.id.taskNameEditText);
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
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mTaskNameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(EXTRA_TASK_NAME, mTaskNameEditText.getText().toString());
                    replyIntent.putExtra(EXTRA_ADDITIONAL_NOTES, mAdditionalNotesEditText.getText().toString());
                    if (mSetDueDateCheckBox.isChecked() && mDatePreview.getVisibility() == View.VISIBLE){
                        calendar.set(mDatePickerFragment.getYear(), mDatePickerFragment.getMonth(), mDatePickerFragment.getDay(), mTimePickerFragment.getHour(), mTimePickerFragment.getMinute(),0);
                        date = calendar.getTime();
                        replyIntent.putExtra(EXTRA_TASK_DUE_DATE, date);
                        if (mNotificationCheckBox.isChecked() && mDatePreview.getVisibility() == View.VISIBLE){
                            replyIntent.putExtra(EXTRA_TASK_NOTIFACTION_SET , true);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}