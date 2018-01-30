package com.austinhlee.android.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    private Button mButton;
    private Button mTimeButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mEditText = (EditText) findViewById(R.id.taskNameEditText);

        mButton = (Button) findViewById(R.id.createButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task(mEditText.getText().toString());
                Database.get(getApplicationContext()).addTask(newTask);
                finish();
            }
        });

        mTimeButton = (Button) findViewById(R.id.pickTimeButton);
    }

}
