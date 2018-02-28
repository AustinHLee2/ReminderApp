package com.austinhlee.android.app1;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Austin Lee on 2/26/2018.
 */

public class DiffCallback extends DiffUtil.Callback {

    List<Task> oldTasks;
    List<Task> newTasks;

    public DiffCallback(List<Task> newTasks, List<Task> oldTasks){
        this.newTasks = newTasks;
        this.oldTasks = oldTasks;
    }

    @Override
    public int getOldListSize() {
        return oldTasks.size();
    }

    @Override
    public int getNewListSize() {
        return newTasks.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTasks.get(oldItemPosition).getId() == newTasks.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTasks.get(oldItemPosition).equals(newTasks.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
