package com.powergroup.unite.chat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powergroup.unite.R;
import com.powergroup.unite.dto.Message;

import java.util.ArrayList;

/**
 * Created by Nicky on 2/2/2017.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final String TAG = "ChatAdapter";

    private ArrayList<Message> dataSet;
    private String owner;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public RelativeLayout messageContainer;

        public RelativeLayout rel;
        public CardView cardView;

        public ViewHolder(RelativeLayout v) {
            super(v);
            rel = v;
            cardView = (CardView) v.findViewById(R.id.card);
            message = (TextView) v.findViewById(R.id.message);
            messageContainer = (RelativeLayout) v.findViewById(R.id.message_container);
        }
    }

    public ChatAdapter(ArrayList<Message> dataSet, String owner) {
        this.dataSet = dataSet;
        this.owner = owner;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_message, parent, false);
        final ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Message message = dataSet.get(position);

        if(message.sender.equals(owner)) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.cardView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            holder.cardView.setLayoutParams(params);
        }

        holder.message.setText(message.message);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setDataSet(ArrayList<Message> messages) {
        this.dataSet = messages;
    }

    public void addMessage(Message message) {
        Log.d(TAG, message.message + "");
        dataSet.add(message);
        notifyItemInserted(dataSet.size()-1);
    }

    public Message getItem(int position) {
        return dataSet.get(position);
    }

    public long getLast() {
        if(dataSet.size() == 0) {
            return 0;
        }
        return dataSet.get(dataSet.size()-1).getTimestamp()+1;
    }

}
