package com.powergroup.unite.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.powergroup.unite.R;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.app.SlidingTabLayout;
import com.powergroup.unite.history.HistoryFragment;
import com.powergroup.unite.unite.UnifyFragment;
import com.powergroup.unite.user_profile.ProfileFragment;

/**
 * Created by bummy on 4/1/17.
 */

public class MainActivity extends GenericActivity {

    //create new variables
    private Fragment[] fragments;
    private SlidingTabLayout tabs;
    private ViewPager pager;
    private MainPagerAdapter mainPagerAdapter;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
        assignVariables(savedInstanceState);
        assignHandlers();

    }

    private void assignViews() {
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void assignVariables(Bundle savedInstanceState) {
        fragments = new Fragment[3];
        fragments[0] = new UnifyFragment();
        fragments[1] = new HistoryFragment();
        fragments[2] = new ProfileFragment();
        //creates new fragents

        tabs.setSplit(false);
        tabs.setTabs(3);
        tabs.setTabSetting(false);
        tabs.setSelectedIndicatorColors(0xFF01a1d5);
        pager.setOffscreenPageLimit(3);

        //basically creates adapter and sets titles
        String[] titles = {"Unify", "History", "Profile"};
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments, titles);
        pager.setAdapter(mainPagerAdapter);
        tabs.setViewPager(pager);
    }

    private void assignHandlers() {

    }
}
