package com.powergroup.unite.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.powergroup.unite.chat.ChatActivity;
import com.powergroup.unite.login.LoginActivity;
import com.powergroup.unite.main.MainActivity;
import com.powergroup.unite.qr.QRActivity;
import com.powergroup.unite.user_profile.CreateProfileActivity;

/**
 * Created by bummy on 4/1/17.
 */

/*
this is made as the base class that all activities derive from,
it's made this way so you can navigate to any other activities given any activity
 */
public class GenericActivity extends FragmentActivity {
    private final String TAG = "GenericActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void navigateToLogin() {
        Log.d(TAG, "Navigated to login.");
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToMain() {
        //starts new activity and navigates to main
        Log.d(TAG, "Navigated to main.");
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToCreateProfile() {
        Log.d(TAG, "Navigated to Create Profile.");
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), CreateProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToQR() {
        Log.d(TAG, "Navigated to QR.");
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), QRActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToChat(String id1, String id2) {
        Log.d(TAG, "Navigated to chat.");
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), ChatActivity.class);
        String key = "";
        key = (id1.compareTo(id2) > 0) ? (id1 + '-' + id2) : (id2 + '-' + id1);
        intent.putExtra("convo_key", key);
        startActivity(intent);
    }
}
