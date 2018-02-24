package com.austinhlee.android.app1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Austin Lee on 1/29/2018.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> mTaskList;
    private final LayoutInflater mInflater;
    private TaskViewModel mTaskViewModel;

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView taskNameTextView;
        private TextView mDueDateTextView;
        private CheckBox mCheckBox;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = (TextView) itemView.findViewById(R.id.taskNameTextView);
            mDueDateTextView = (TextView) itemView.findViewById(R.id.dueDateTextView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.taskCheckBox);
        }

    }


    public TaskListAdapter(Context context, TaskViewModel taskViewModel) {
        mTaskViewModel = taskViewModel;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_layout_compact, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.TaskViewHolder holder, int position) {
        if (mTaskList != null){
            Task current = mTaskList.get(position);
            holder.taskNameTextView.setText(current.getTaskName());
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        removeAt(position);
                        mTaskViewModel.deleteTask(current.getTaskName());
                    }
                }
            });
            if (current.getDueDate() != null) {
                holder.mDueDateTextView.setText(current.getDueDate().toString());
            }
        } else {
            holder.taskNameTextView.setText("No task name");
        }
    }


    void setTasks(List<Task> tasks){
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    public void removeAt(int position){
        mTaskList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if (mTaskList != null) {
            return mTaskList.size();
        }
        else return 0;
    }
}
