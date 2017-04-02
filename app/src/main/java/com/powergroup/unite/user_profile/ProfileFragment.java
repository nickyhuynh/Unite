package com.powergroup.unite.user_profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.Profile;
import com.squareup.picasso.Picasso;

/**
 * Created by bummy on 4/2/17.
 */

public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";
    private ImageView profileImage;
    private TextView username;
    private TextView userAge;
    private TextView userBio;
    private TextView gender;
    public TextView countries;
    public TextView ethnicities;
    public TextView languages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        assignViews(rootView);
        assignVariables(savedInstanceState);
        assignHandlers();

        return rootView;
    }

    private void assignViews(View rootView) {
        profileImage = (ImageView) rootView.findViewById(R.id.profile_image);
        username = (TextView) rootView.findViewById(R.id.user_name);
        userAge = (TextView) rootView.findViewById(R.id.user_age);
        userBio = (TextView) rootView.findViewById(R.id.user_bio);
        gender = (TextView) rootView.findViewById(R.id.gender);
        countries = (TextView) rootView.findViewById(R.id.countries_list);
        ethnicities = (TextView) rootView.findViewById(R.id.ethnicities_list);
        languages = (TextView) rootView.findViewById(R.id.languages_list);
    }

    private void assignVariables(Bundle savedInstanceState) {
        Profile.ProfileInfo info = Profile.INSTANCE.info;
        Picasso.with(Application.getInstance()).load(info.pic).into(profileImage);
        username.setText(info.name);
        userAge.setText(info.age);
        userBio.setText(info.bio);
        gender.setText(info.gender);
        countries.setText(info.nation);
        ethnicities.setText(info.ethnicities.toString());
        languages.setText(info.languages.toString());
    }

    private void assignHandlers() {

    }
}
