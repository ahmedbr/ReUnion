package com.safaorhan.reunion.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.model.Message;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
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

    public class MessageHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView senderTextView;
        TextView messageTextView;

        public MessageHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.sender_Text_View);
            messageTextView = itemView.findViewById(R.id.message_Text_View);
        }

        public void bind(Message message) {

        }
    }
}
