package com.austinhlee.android.app1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Random;

/**
 * Created by Austin Lee on 1/29/2018.
 */

public class Task {

    private String mTaskName;
    private Date mCreationDate;
    private Date mDueDate;
    private int mId;


    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        mTaskName = taskName;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public int getId() {
        return mId;
    }

    public void setId() {
//        Context context = activity;
        mId = new Random().nextInt();
       /* SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Integer.toString(mId), mId);
        editor.apply();*/
    }
}
