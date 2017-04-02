package com.powergroup.unite.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.GenericActivity;

/**
 * Created by bummy on 4/1/17.
 */

public class LoginActivity extends GenericActivity {
    private final String TAG = "LoginActivity";

    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignViews();
        assignVariables(savedInstanceState);
        assignHandlers();
    }

    private void assignViews() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
    }

    private void assignVariables(Bundle savedInstanceStates) {
        callbackManager = CallbackManager.Factory.create();
    }

    private void assignHandlers() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Logged in!");
                navigateToCreateProfile();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Failed logging in!");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "Message: " + error.getMessage());
                Toast.makeText(Application.getInstance(), "Hey there! Looks like there was some error with your Facebook login. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
