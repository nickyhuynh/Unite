package com.powergroup.unite.unite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.powergroup.unite.R;
import com.powergroup.unite.chat.ChatActivity;

/**
 * Created by bummy on 4/1/17.
 */

public class UnifyFragment extends Fragment {

    private TextView gender;
    private TextView bio;

    private TextView reject;
    private TextView accept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_unite, container, false);

        assignViews(rootView);
        assignVariables(savedInstanceState);
        assignHandlers();

        return rootView;
    }

    private void assignViews(View rootView) {
        gender = (TextView) rootView.findViewById(R.id.user_gender);
        bio = (TextView) rootView.findViewById(R.id.user_bio);

        reject = (TextView) rootView.findViewById(R.id.reject);
        accept = (TextView) rootView.findViewById(R.id.accept);
    }

    private void assignVariables(Bundle savedInstanceState) {
        FirebaseDatabase.getInstance().getReference().child("/profiles").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void assignHandlers() {

    }
}
