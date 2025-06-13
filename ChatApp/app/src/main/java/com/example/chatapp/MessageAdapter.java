package com.example.chatapp;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Message> messages;
    private final String currentUser;

    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    public MessageAdapter(ArrayList<Message> messages, String currentUser) {
        this.messages = messages;
        this.currentUser = currentUser;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).sender.equals(currentUser) ? TYPE_SENT : TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = messages.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).messageText.setText(msg.message);
            ((SentViewHolder) holder).timestampText.setText(msg.timestamp);
        } else {
            ((ReceivedViewHolder) holder).messageText.setText(msg.message);
            ((ReceivedViewHolder) holder).timestampText.setText(msg.timestamp);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, timestampText;

        SentViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.sentMessage);
            timestampText = itemView.findViewById(R.id.sentTime);
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, timestampText;

        ReceivedViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.receivedMessage);
            timestampText = itemView.findViewById(R.id.receivedTime);
        }
    }
}
