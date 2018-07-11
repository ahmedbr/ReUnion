package com.safaorhan.reunion.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.DocumentReference;
import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.adapter.ConversationAdapter;
import com.safaorhan.reunion.adapter.MessageAdapter;
import com.safaorhan.reunion.model.Message;

public class MessageActivity extends AppCompatActivity {
    RecyclerView messageRecycleView;
    EditText messageEditeText;
    MessageAdapter messageAdapter;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        String conversationId = intent.getStringExtra("conversation_id");

        messageEditeText = findViewById(R.id.message_Edit_Text);
        messageRecycleView = findViewById(R.id.message_Recycle_View);

        documentReference = FirestoreHelper.getConversationRefById(conversationId);
        messageAdapter = MessageAdapter.get(documentReference);

        messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
        messageRecycleView.setAdapter(messageAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.send_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditeText.getText().toString().trim();
                if(!message.equals("")){
                    FirestoreHelper.sendMessage(message, documentReference);
                    finish();
                    startActivity(getIntent());
                }
                messageEditeText.setText("");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}
