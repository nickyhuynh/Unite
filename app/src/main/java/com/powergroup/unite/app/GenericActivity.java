package com.powergroup.unite.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.powergroup.unite.main.MainActivity;

/**
 * Created by bummy on 4/1/17.
 */

/*
this is made as the base class that all activities derive from,
it's made this way so you can navigate to any other activities given any activity
 */
public class GenericActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void navigateToMain() {
        //starts new activity and navigates to main
        Intent intent = new Intent();
        intent.setClass(Application.getInstance(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
