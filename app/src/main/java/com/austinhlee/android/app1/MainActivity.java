package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Database myDataset;
    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        myDataset = Database.get(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyAdapter(myDataset.getTasks());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(mContext, SecondActivity.class);
                startActivityForResult(intent,1);
                return true;

            case R.id.action_sort:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.sort_by_alphabetical:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        return firstTask.getTaskName().compareTo(secondTask.getTaskName());
                    }
                });
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_create:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        return firstTask.getCreationDate().compareTo(secondTask.getCreationDate());
                    }
                });
                mAdapter.notifyDataSetChanged();

                return true;

            case R.id.sort_by_due:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        if (firstTask.getDueDate() == null) {
                            return (secondTask.getDueDate() == null) ? 0 : -1;
                        }
                        if (secondTask.getDueDate() == null) {
                            return 1;
                        }
                        return secondTask.getDueDate().compareTo(firstTask.getDueDate());
                    }
                });
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.compact_view:
                return true;

            case R.id.detailed_view:
                return true;

            case R.id.action_edit:
                return true;

            case R.id.action_options:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                mTask = new Task();
                String taskName = data.getStringExtra("taskName");
                Boolean dueDateSet = data.getBooleanExtra("dueDateSet",false);
                if (dueDateSet) {
                    Date dueDate = (Date) data.getSerializableExtra("dueDate");
                    mTask.setDueDate(dueDate);
                }
                Date creationDate = (Date)data.getSerializableExtra("creationDate");
                mTask.setTaskName(taskName);
                mTask.setCreationDate(creationDate);
                myDataset.addTask(mTask);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
