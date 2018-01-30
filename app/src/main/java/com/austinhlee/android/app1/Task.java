package com.austinhlee.android.app1;

import java.util.Date;

/**
 * Created by Austin Lee on 1/29/2018.
 */

public class Task {

    private String mTaskName;
    private Date mDate;

    public Task(String taskName){

        mTaskName = taskName;
        mDate = new Date();

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

}
