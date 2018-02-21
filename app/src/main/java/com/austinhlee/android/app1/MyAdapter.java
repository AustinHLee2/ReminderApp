package com.austinhlee.android.app1;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Austin Lee on 1/29/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static List<Task> mDataset;
    private ItemTouchHelper touchHelper;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTaskName;
        public TextView mDateTextView;
        public TextView mDueDateTextview;
        public CheckBox mCheckBox;
        public Context mContext;
        public Task mTask;
        public ImageView mImageView;

        public ViewHolder(CardView v){
            super(v);
            mContext = v.getContext();
            mTaskName = v.findViewById(R.id.taskNameTextView);
//            mDateTextView = v.findViewById(R.id.dateTextView);
            mCheckBox = v.findViewById(R.id.taskCheckBox);
            mDueDateTextview = v.findViewById(R.id.dueDateTextView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTask = mDataset.get(getAdapterPosition());
                    Intent intent = new Intent(mContext,TaskDetailActivity.class);
                    intent.putExtra("taskName", mTask.getTaskName());
                    if (mTask.getDueDate() != null) {
                        intent.putExtra("taskDueDate", mTask.getDueDate());
                    }
                    intent.putExtra("taskCreationDate", mTask.getCreationDate());
                    mContext.startActivity(intent);
                    System.out.println("hello");
                }
            });
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        removeItem(getAdapterPosition());
                    }
                }
            });
        }
    }

    public MyAdapter(List<Task> myDataset){
        mDataset = myDataset;
    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_compact,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.mTaskName.setText(mDataset.get(position).getTaskName());
        holder.mDueDateTextview.setVisibility(View.INVISIBLE);
        holder.mCheckBox.setChecked(false);
//        holder.mDateTextView.setText(mDataset.get(position).getCreationDate().toString());
        if (mDataset.get(position).getDueDate() != null) {
            holder.mDueDateTextview.setVisibility(View.VISIBLE);
            holder.mDueDateTextview.setText(formatDueDate(mDataset.get(position).getDueDate()));
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void removeItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }

    private String formatDueDate(Date date){
        DateFormat df4 = new SimpleDateFormat("dd MMM yy");
        String str2 = df4.format(date);
        return str2;
    }

}
