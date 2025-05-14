package com.example.task81candroidappexample.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task81candroidappexample.R;
import com.example.task81candroidappexample.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> chatList;

    public ChatAdapter(List<ChatMessage> chatList) {
        this.chatList = chatList;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage chat = chatList.get(position);
        holder.messageText.setText(chat.getMessage());


        if (chat.isUser()) {
            holder.messageText.setBackgroundColor(Color.parseColor("#BBDEFB"));
            holder.messageText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.messageText.setBackgroundColor(Color.parseColor("#E0E0E0"));
            holder.messageText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
