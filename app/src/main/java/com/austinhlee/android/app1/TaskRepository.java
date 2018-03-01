package com.austinhlee.android.app1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Austin Lee on 2/24/2018.
 */

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    TaskRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mTaskDao = db.TaskDao();
        mAllTasks = mTaskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks(){
        return mAllTasks;
    }

    public void insert (Task task){
        new insertAsyncTask(mTaskDao).execute(task);
    }

    public void delete (String taskName){
        new deleteAsyncTask(mTaskDao).execute(taskName);
    }

    public void deleteByID (int id) {
        new deleteByIDAsyncTask(mTaskDao).execute(id);
    }

    public Task getTaskByID(int id){
        try {
            return new getByIDAsyncTask(mTaskDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void>{

        private TaskDao mAsyncTaskDao;

        deleteAsyncTask(TaskDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params){
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getByIDAsyncTask extends AsyncTask<Integer, Void, Task>{

        private TaskDao mAsyncTaskDao;

        getByIDAsyncTask(TaskDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Task doInBackground(final Integer... params){
            return mAsyncTaskDao.findByID(params[0]);
        }
    }

    private static class deleteByIDAsyncTask extends AsyncTask<Integer, Void, Void>{

        private TaskDao mAsyncTaskDao;

        deleteByIDAsyncTask(TaskDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params){
            mAsyncTaskDao.deleteByID(params[0]);
            return null;
        }
    }



}
