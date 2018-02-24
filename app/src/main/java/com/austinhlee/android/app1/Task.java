package com.austinhlee.android.app1;

import android.app.Activity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Random;

/**
 * Created by Austin Lee on 1/29/2018.
 */

@Entity(tableName = "tasks")
public class Task {


    @ColumnInfo(name = "task_name")
    private String mTaskName;

    @ColumnInfo(name = "creation_date")
    private Date mCreationDate;

    @ColumnInfo(name = "due_date")
    private Date mDueDate;

    @PrimaryKey(autoGenerate = true)
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

    public void setId(int id) {
        mId = id;
    }
}
