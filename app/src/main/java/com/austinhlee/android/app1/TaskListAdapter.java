package com.austinhlee.android.app1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private LayoutInflater mInflater;
    private TaskViewModel mTaskViewModel;
    private Context mContext;

    public static String EXTRA_TASK_ID = "com.austinhlee.android:app1.TASK_ID";

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView taskNameTextView;
        private TextView mDueDateTextView;
        private CheckBox mCheckBox;
        private ImageView mNotificationIconImageView;

        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = (TextView) itemView.findViewById(R.id.taskNameTextView);
            mNotificationIconImageView = (ImageView) itemView.findViewById(R.id.notificicationSetImageView);
            mDueDateTextView = (TextView) itemView.findViewById(R.id.dueDateTextView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.taskCheckBox);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.foreground_card_view);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.item_background);
        }

    }


    public TaskListAdapter(Context context, TaskViewModel taskViewModel) {
        mContext = context;
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
            if (current.getDueDate() != null) {
                holder.mDueDateTextView.setVisibility(View.VISIBLE);
                holder.mDueDateTextView.setText(formatDate(current.getDueDate()));
            } else {
                holder.mDueDateTextView.setVisibility(View.INVISIBLE);
            }
            if (current.isHasNotification() == true){
                holder.mNotificationIconImageView.setVisibility(View.VISIBLE);
            } else {
                holder.mNotificationIconImageView.setVisibility(View.INVISIBLE);
            }
            holder.mCheckBox.setChecked(false);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (current.getDueDate() != null) {
                            current.setHasNotification(false);
                            NotificationScheduler.cancelReminder(mContext, AlarmReceiver.class, current.getId());
                        }
                        removeAt(position);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra(EXTRA_TASK_ID, current.getId());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.taskNameTextView.setText("No task name");
        }

    }


    void setTasks(List<Task> tasks){
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    public void removeAt(int position){
        mTaskViewModel.deleteTask(mTaskList.get(position).getTaskName());
        this.setTasks(mTaskList);
    }

    @Override
    public int getItemCount() {
        if (mTaskList != null) {
            return mTaskList.size();
        }
        else return 0;
    }

    private String formatDate(Date date){
        DateFormat df = new SimpleDateFormat("dd MMM yy");
        String dateString = df.format(date);
        return dateString;
    }
}
