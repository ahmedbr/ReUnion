package com.safaorhan.reunion.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.firebase.firestore.DocumentReference;
import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.adapter.MessageAdapter;

public class MessageActivity extends AppCompatActivity {
    RecyclerView messageRecycleView;
    EditText messageEditText;
    MessageAdapter messageAdapter;
    DocumentReference documentReference;
    LinearLayoutManager layoutManager;
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        String conversationId = intent.getStringExtra("conversation_id");

        messageEditText = findViewById(R.id.message_Edit_Text);
        messageRecycleView = findViewById(R.id.message_Recycle_View);

        try {
            documentReference = FirestoreHelper.getConversationRefById(conversationId);
            messageAdapter = MessageAdapter.get(documentReference);
        } catch (Exception e) {

        }

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setStackFromEnd(true);
        messageRecycleView.setHasFixedSize(true);
        messageRecycleView.setLayoutManager(layoutManager);
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                layoutManager.smoothScrollToPosition(messageRecycleView,null,messageAdapter.getItemCount());
            }
        });
        messageRecycleView.setAdapter(messageAdapter);


        FloatingActionButton floatingActionButton = findViewById(R.id.send_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();
                if (!message.equals("")) {
                    FirestoreHelper.sendMessage(message, documentReference);
                }
                messageEditText.setText("");
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        messageAdapter.startListening();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
        messageAdapter.stopListening();
    }
}
