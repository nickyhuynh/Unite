package com.powergroup.unite.user_profile;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.CircularImageView;
import com.powergroup.unite.app.GenericActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Elise on 4/1/2017.
 *
 * User's profile, which will feature name, age, bio, the flag icon and nation that person is representing, what sports, etc.
 */

public class UserProfile extends GenericActivity {

    private TextView userName;
    private TextView userAge;
    private TextView userGender;
    private TextView userCountry;
    private TextView userLanguages;
    private TextView userBio;
    protected ImageView previewImageView;
    public ImageView profilePic;
    public Button editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        // initialize variables
        profilePic = (ImageView) findViewById(R.id.profile_image);
        userName = (TextView) findViewById(R.id.user_name);
        userAge = (TextView) findViewById(R.id.user_age);
        userGender = (TextView) findViewById(R.id.user_gender);
        userCountry = (TextView) findViewById(R.id.user_country);
        userLanguages = (TextView) findViewById(R.id.user_languages);
        userBio = (TextView) findViewById(R.id.user_bio);
        editButton = (Button) findViewById(R.id.edit_button);

        // setting profile pic to sample
        // Bitmap sampleProfilePic = BitmapFactory.decodeResource(getResources(), R.drawable.sample_profile_pic);

//        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.sample_profile_pic));
        Picasso.with(Application.getInstance()).load(R.drawable.sample_profile_pic).into(profilePic);

        final Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent();
                intent.setClass(UserProfile.this, EditProfileActivity.class);
                startActivity(intent);

            }
        });
    }
}
