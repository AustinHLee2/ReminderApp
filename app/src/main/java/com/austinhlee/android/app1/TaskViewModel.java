package com.austinhlee.android.app1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

/**
 * Created by Austin Lee on 2/23/2018.
 */

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mTaskRepository;
    private LiveData<List<Task>> mAllTasks;

    public TaskViewModel (Application application){
        super(application);
        mTaskRepository = new TaskRepository(application);
        mAllTasks = mTaskRepository.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks(){
        return mAllTasks;
    }

    public void insert(Task task){
        mTaskRepository.insert(task);
    }

    public void deleteTask(String taskName){
        mTaskRepository.delete(taskName);
    }

}
