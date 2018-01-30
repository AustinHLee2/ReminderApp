package com.austinhlee.android.app1;

import android.content.Context;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin Lee on 1/29/2018.
 */

public class Database {

    private static Database sDatabase;

    private List<Task> mTasks;

    public static Database get(Context context){
        if (sDatabase == null){
            sDatabase = new Database(context);
        }
        return sDatabase;
    }

    private Database(Context context){
        mTasks = new ArrayList<>();
    }

    public void addTask(Task c){
        mTasks.add(c);
    }

    public List<Task> getTasks(){
        return mTasks;
    }

}
