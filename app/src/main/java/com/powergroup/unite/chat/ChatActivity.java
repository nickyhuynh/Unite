package com.powergroup.unite.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.powergroup.unite.R;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.dto.Message;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by bummy on 4/1/17.
 */

public class ChatActivity extends GenericActivity {
    private static final String TAG = "ChatActivity";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType currentLayoutManagerType;
    protected RecyclerView recyclerView;
    protected ChatAdapter chatAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    private String chatroom;
    private DatabaseReference database;

    private TextView sendButton;
    private EditText messageField;

    private String owner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent data = getIntent();

        String key = data.getStringExtra("convo_key");
        if(key != null) {
            chatroom = key;
        }

        assignViews();
        assignVariables(savedInstanceState);
        assignHandlers();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    private void assignViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sendButton = (TextView) findViewById(R.id.send_button);
        messageField = (EditText)findViewById(R.id.message_field);
    }

    private void assignVariables(Bundle savedInstanceState) {
        owner = "050";
        layoutManager = new LinearLayoutManager(this);
        chatAdapter = new ChatAdapter(new ArrayList<Message>(), owner);
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            currentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(currentLayoutManagerType);
        recyclerView.setAdapter(chatAdapter);
        database = FirebaseDatabase.getInstance().getReference("conversations/" + chatroom);

        Log.d(TAG, database.toString());

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()) {
                    Message message = new Message();
                    message.message = "Hi there from Unify! This is an automated message that marks the beginning of your conversation with your new friend!";
                    message.sender = "Unify";
                    message.timestamp = System.currentTimeMillis();
                    database.push().setValue(message);
                }

                for(DataSnapshot message : dataSnapshot.getChildren()) {
                    Log.d(TAG, message.getKey()+"");
                    Log.d(TAG, message.getValue()+"");
                    Message mem = message.getValue(Message.class);
                    chatAdapter.addMessage(mem);
                }

                ValueEventListener messageListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        database.orderByChild("timestamp").startAt(chatAdapter.getLast()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot message : dataSnapshot.getChildren()) {
                                    Message mem = message.getValue(Message.class);
                                    chatAdapter.addMessage(mem);
                                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };
                database.addValueEventListener(messageListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void assignHandlers() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageField.getText().toString().isEmpty()) {
                    Message message = new Message();
                    message.message = messageField.getText().toString();
                    message.sender = "050";
                    message.timestamp = System.currentTimeMillis();
                    database.push().setValue(message);
                    messageField.getText().clear();
                }
            }
        });
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(this, SPAN_COUNT);
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(this);
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                layoutManager = new LinearLayoutManager(this);
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
