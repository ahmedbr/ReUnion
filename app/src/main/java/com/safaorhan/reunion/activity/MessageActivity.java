package com.safaorhan.reunion.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.safaorhan.reunion.R;

public class MessageActivity extends AppCompatActivity {
    RecyclerView messageRecycleView;
    EditText messageEditeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageEditeText = findViewById(R.id.message_Edit_Text);
        FloatingActionButton floatingActionButton = findViewById(R.id.send_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
