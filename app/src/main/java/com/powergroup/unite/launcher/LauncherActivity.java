package com.powergroup.unite.launcher;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.powergroup.unite.R;
import com.powergroup.unite.app.GenericActivity;

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
        new CountDownTimer(1000, 20) {
            @Override
            public void onTick(long tick) {

            }

            @Override
            public void onFinish() {
                if(AccessToken.getCurrentAccessToken() != null) {
//                    navigateToMain();
                    navigateToQR();
                } else {
                    navigateToLogin();
                }
            }
        }.start();
    }

    private void assignHandlers() {

    }
}
