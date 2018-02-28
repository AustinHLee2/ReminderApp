package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> mTaskList;
    private LayoutInflater mInflater;
    private TaskViewModel mTaskViewModel;
    private Context mContext;

    public static String EXTRA_TASK_ID = "com.austinhlee.android:app1.TASK_ID";
    public static String EDIT_TASK_KEY;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;

    public static final int COMPACT_VIEW_TYPE = 0;

    public static final int DETAILED_VIEW_TYPE = 1;

    private int currentViewType;

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

    static class TaskViewHolderDetailed extends TaskViewHolder {

        private TextView taskNameTextView;
        private TextView mDueDateTextView;
        private TextView mCreationDateTextView;
        private CheckBox mCheckBox;
        private ImageView mNotificationIconImageView;

        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;

        TaskViewHolderDetailed(View itemView) {
            super(itemView);
            taskNameTextView = (TextView) itemView.findViewById(R.id.taskNameTextView);
            mNotificationIconImageView = (ImageView) itemView.findViewById(R.id.notificicationSetImageView);
            mDueDateTextView = (TextView) itemView.findViewById(R.id.dueDateTextView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.taskCheckBox);
            mCreationDateTextView = (TextView) itemView.findViewById(R.id.creationDateTextview);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMPACT_VIEW_TYPE: {
                View itemView = mInflater.inflate(R.layout.item_layout_compact, parent, false);
                return new TaskViewHolder(itemView);
            }
            case DETAILED_VIEW_TYPE: {
                View itemViewDetailed = mInflater.inflate(R.layout.item_layout_detailed, parent, false);
                return new TaskViewHolderDetailed(itemViewDetailed);
            }
            default: {
                Log.d("fewaaaaaafaewwwwww", "Default reached");
                return null;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return currentViewType;
    }

    void setCurrentViewType(int currentViewType) {
        this.currentViewType = currentViewType;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0: {
                TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
                if (mTaskList != null) {
                    Task current = mTaskList.get(position);
                    taskViewHolder.taskNameTextView.setText(current.getTaskName());
                    if (current.getDueDate() != null) {
                        taskViewHolder.mDueDateTextView.setVisibility(View.VISIBLE);
                        taskViewHolder.mDueDateTextView.setText(formatDate(current.getDueDate()));
                    } else {
                        taskViewHolder.mDueDateTextView.setVisibility(View.INVISIBLE);
                    }
                    if (current.isHasNotification()) {
                        taskViewHolder.mNotificationIconImageView.setVisibility(View.VISIBLE);
                    } else {
                        taskViewHolder.mNotificationIconImageView.setVisibility(View.INVISIBLE);
                    }
                    taskViewHolder.mCheckBox.setChecked(false);
                    taskViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                    taskViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, TaskDetailActivity.class);
                            intent.putExtra(EXTRA_TASK_ID, current.getId());
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    taskViewHolder.taskNameTextView.setText("No task name");
                }
                break;
            }
            case 1: {
                TaskViewHolderDetailed taskViewHolderDetailed = (TaskViewHolderDetailed) holder;
                if (mTaskList != null) {
                    Task current = mTaskList.get(position);
                    taskViewHolderDetailed.taskNameTextView.setText(current.getTaskName());
                    taskViewHolderDetailed.mCreationDateTextView.setText(current.getCreationDate().toString());
                    if (current.getDueDate() != null) {
                        taskViewHolderDetailed.mDueDateTextView.setVisibility(View.VISIBLE);
                        taskViewHolderDetailed.mDueDateTextView.setText(formatDate(current.getDueDate()));
                    } else {
                        taskViewHolderDetailed.mDueDateTextView.setVisibility(View.INVISIBLE);
                    }
                    if (current.isHasNotification()) {
                        taskViewHolderDetailed.mNotificationIconImageView.setVisibility(View.VISIBLE);
                    } else {
                        taskViewHolderDetailed.mNotificationIconImageView.setVisibility(View.INVISIBLE);
                    }
                    taskViewHolderDetailed.mCheckBox.setChecked(false);
                    taskViewHolderDetailed.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                    taskViewHolderDetailed.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, TaskDetailActivity.class);
                            intent.putExtra(EXTRA_TASK_ID, current.getId());
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    taskViewHolderDetailed.taskNameTextView.setText("No task name");
                }
                break;
            }
        }
    }


    void setTasks(List<Task> tasks){
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    public void removeAt(int position){
        mTaskViewModel.deleteTask(mTaskList.get(position).getId());
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
