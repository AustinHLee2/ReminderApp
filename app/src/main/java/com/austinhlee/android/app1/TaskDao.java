package com.austinhlee.android.app1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Austin Lee on 2/23/2018.
 */
@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE task_name LIKE :taskName ")
    Task findByName(String taskName);

    @Insert
    void insert(Task task);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("DELETE FROM tasks WHERE task_name LIKE :taskName ")
    void deleteTask(String taskName);
}
