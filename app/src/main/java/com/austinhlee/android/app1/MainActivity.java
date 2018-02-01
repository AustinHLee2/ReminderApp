package com.austinhlee.android.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Database myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();

    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
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
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
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
                        return firstTask.getDate().compareTo(secondTask.getDate());
                    }
                });
                mAdapter.notifyDataSetChanged();

                return true;

            case R.id.sort_by_due:
                Collections.sort(myDataset.get(mContext).getTasks(), new Comparator<Task>() {
                    @Override
                    public int compare(Task firstTask, Task secondTask) {
                        return firstTask.getDueDate().compareTo(secondTask.getDueDate());
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

    private void updateUI(){
        myDataset = Database.get(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(myDataset.getTasks());
        mRecyclerView.setAdapter(mAdapter);

    }

    private String converToComparableString(String date){
        return null;
    }
}
