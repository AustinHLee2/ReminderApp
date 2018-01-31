package com.austinhlee.android.app1;

import java.util.Date;

/**
 * Created by Austin Lee on 1/29/2018.
 */

public class Task {

    private String mTaskName;
    private Date mDate;
    private String mDueDate;

    public Task(String taskName){

        mTaskName = taskName;
        mDate = new Date();
        mDueDate = new String();
    }

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        mTaskName = taskName;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }
}
