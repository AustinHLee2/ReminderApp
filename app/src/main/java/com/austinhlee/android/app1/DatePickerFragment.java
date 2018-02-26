package com.austinhlee.android.app1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int mYear;
    int mMonth;
    int mDay;
    private TextView mDatePreview;
    private CheckBox mCheckBox;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        mCheckBox = (CheckBox)getActivity().findViewById(R.id.notificationCheckBox);
        mDatePreview = (TextView)getActivity().findViewById(R.id.date_preview);
        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mCheckBox.setVisibility(View.VISIBLE);
        mDatePreview.setVisibility(View.VISIBLE);
        mDatePreview.setText((month+1)+"/"+day+"/"+year);
    }
}


