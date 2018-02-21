package com.austinhlee.android.app1;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    int mHour;
    int mMinute;
    TextView mTextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        mTextView = getActivity().findViewById(R.id.time_preview);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        mTextView.setVisibility(View.VISIBLE);
        Calendar cal = Calendar.getInstance();
        cal.set(2018,4,20,hourOfDay,minute);
        Date dueDate = (Date)cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        mTextView.setText(dateFormat.format(dueDate));
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute;
    }
}


