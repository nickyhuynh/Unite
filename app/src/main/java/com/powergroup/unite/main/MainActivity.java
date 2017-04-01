package com.powergroup.unite.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.powergroup.unite.R;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.app.SlidingTabLayout;
import com.powergroup.unite.history.HistoryFragment;
import com.powergroup.unite.unite.UniteFragment;

/**
 * Created by bummy on 4/1/17.
 */

public class MainActivity extends GenericActivity {

    //create new variables
    private Fragment[] fragments;
    private SlidingTabLayout tabs;
    private ViewPager pager;
    private MainPagerAdapter mainPagerAdapter;

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
        fragments = new Fragment[2];
        fragments[0] = new UniteFragment();
        fragments[1] = new HistoryFragment();
        //creates new fragents

        tabs.setSplit(false);
        tabs.setTabs(2);
        tabs.setTabSetting(false);
        tabs.setSelectedIndicatorColors(R.color.colorPrimary);
        pager.setOffscreenPageLimit(2);

        //basically creates adapter and sets titles
        String[] titles = {"Unite", "History"};
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments, titles);
        pager.setAdapter(mainPagerAdapter);
        tabs.setViewPager(pager);
    }

    private void assignHandlers() {

    }
}
