package com.austinhlee.android.app1;

import java.util.Date;

/**
 * Created by Austin Lee on 1/29/2018.
 */

public class Task {

    private String mTaskName;
    private Date mCreationDate;
    private Date mDueDate;


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
}
