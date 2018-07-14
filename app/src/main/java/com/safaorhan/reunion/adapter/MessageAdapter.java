package com.safaorhan.reunion.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.model.Message;
import com.safaorhan.reunion.model.User;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder> {

    private MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    public static MessageAdapter get(DocumentReference conversationRef) {
        Query query = FirebaseFirestore.getInstance()
                .collection("messages")
                .orderBy("sentAt")
                .whereEqualTo("conversation", conversationRef);

        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        return new MessageAdapter(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageAdapter.MessageHolder holder,
                                    int position, @NonNull Message message) {
        holder.bind(message);
    }

    @NonNull
    @Override
    public MessageAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageHolder(view);
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        TextView senderTextView;
        TextView messageTextView;
        CardView cardView;

        MessageHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.sender_Text_View);
            messageTextView = itemView.findViewById(R.id.message_Text_View);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(Message message) {
                message.getFrom().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        String username = user.getName();
                        senderTextView.setText(username);
                    }
                });
                messageTextView.setText(message.getText());
        }
    }
}
