package com.austinhlee.android.app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationScheduler.showNotification(context, MainActivity.class, intent.getStringExtra("taskTitle"), intent.getStringExtra("additionalNotes"));
    }
}
