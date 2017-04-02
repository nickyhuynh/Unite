package com.powergroup.unite.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.powergroup.unite.R;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.app.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;

/**
 * Created by bummy on 4/1/17.
 */

public class LauncherActivity extends GenericActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        assignViews();
        assignVariables(savedInstanceState);
        assignViews();
    }

    private void assignViews() {

    }

    private void assignVariables(Bundle savedInstanceState) {
        new CountDownTimer(0, 20) {
            @Override
            public void onTick(long tick) {

            }

            @Override
            public void onFinish() {
                if(AccessToken.getCurrentAccessToken() != null) {
                    SharedPreferences preferences = getSharedPreferences("UNIFY", Context.MODE_PRIVATE);
                    if(preferences.contains("profile_info")) {
                        Log.d("Launcher", preferences.getString("profile_info", ""));
                        Profile.INSTANCE.info = new Gson().fromJson(preferences.getString("profile_info", ""), Profile.ProfileInfo.class);
                        Log.d("Launcher", Profile.INSTANCE.info.id);
                        navigateToMain();
                    } else {
                        navigateToCreateProfile();
                    }
                } else {
                    navigateToLogin();
                }
            }
        }.start();
    }

    private void assignHandlers() {

    }
}
