package com.safaorhan.reunion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.adapter.UserAdapter;
import com.safaorhan.reunion.model.User;

public class UsersActivity extends AppCompatActivity implements UserAdapter.UserClickListener {

    private static final String TAG = UsersActivity.class.getSimpleName();

    RecyclerView recyclerView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.recyclerView);

        userAdapter = UserAdapter.get();
        userAdapter.setUserClickListener(this);

        //hello guys it s sez

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }

    @Override
    public void onUserClick(final DocumentReference userRef, final String name) {
        FirestoreHelper.findOrCreateConversation(userRef, new FirestoreHelper.DocumentReferenceCallback() {
            @Override
            public void onCompleted(DocumentReference documentReference) {
                String id = documentReference.getId();
                String userName = name;
                Intent intent = new Intent(UsersActivity.this, MessageActivity.class);
                intent.putExtra("conversation_id", id);
                intent.putExtra("name", userName);
                startActivity(intent);
            }
        });
    }


}
