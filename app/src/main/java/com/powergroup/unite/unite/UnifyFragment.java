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
import com.powergroup.unite.app.Profile;
import com.powergroup.unite.chat.ChatActivity;
import com.powergroup.unite.main.MainActivity;

/**
 * Created by bummy on 4/1/17.
 */

public class UnifyFragment extends Fragment {

    private TextView gender;
    private TextView bio;
    private String id;

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
                int randomNum = 0 + (int)(Math.random() * dataSnapshot.getChildrenCount());
                int i = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(i == randomNum) {
                        Profile.ProfileInfo info = snapshot.getValue(Profile.ProfileInfo.class);
                        gender.setText(info.gender);
                        bio.setText(info.bio);
                        id = info.id;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void assignHandlers() {
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("/profiles").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int randomNum = 0 + (int)(Math.random() * dataSnapshot.getChildrenCount());
                        int i = 0;
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(i == randomNum) {
                                Profile.ProfileInfo info = snapshot.getValue(Profile.ProfileInfo.class);
                                gender.setText(info.gender);
                                bio.setText(info.bio);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).navigateToChat(Profile.INSTANCE.info.id, id);
            }
        });
    }
}
