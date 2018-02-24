package com.austinhlee.android.app1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.*;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by Austin Lee on 2/23/2018.
 */

@android.arch.persistence.room.Database(entities = {Task.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao TaskDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "task_database")
                            .addCallback(sRoomDatabaseCallbac)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallbac = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TaskDao mDao;

        PopulateDbAsync(AppDatabase db){
            mDao = db.TaskDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mDao.deleteAll();
            Task task = new Task();
            task.setTaskName("task test");
            mDao.insert(task);
            return null;
        }
    }
}
