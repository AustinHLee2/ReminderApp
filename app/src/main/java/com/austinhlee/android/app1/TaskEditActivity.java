package com.austinhlee.android.app1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskEditActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        mContext = this;
    }
}
