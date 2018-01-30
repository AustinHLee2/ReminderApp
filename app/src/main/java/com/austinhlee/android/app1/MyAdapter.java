package com.austinhlee.android.app1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Austin Lee on 1/29/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static List<Task> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTaskName;
        public TextView mDateTextView;
        public CheckBox mCheckBox;

        public ViewHolder(CardView v){
            super(v);
            mTaskName = v.findViewById(R.id.taskNameTextView);
            mDateTextView = v.findViewById(R.id.dateTextView);
            mCheckBox = v.findViewById(R.id.taskCheckBox);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("hello");
                }
            });
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   removeItem(getAdapterPosition());
                }
            });
        }
    }

    public MyAdapter(List<Task> myDataset){
        mDataset = myDataset;
    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.mTaskName.setText(mDataset.get(position).getTaskName());
        holder.mDateTextView.setText(mDataset.get(position).getDate().toString());

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

}
