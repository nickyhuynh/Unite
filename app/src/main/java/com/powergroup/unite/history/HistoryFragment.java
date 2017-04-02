package com.powergroup.unite.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powergroup.unite.R;
import com.powergroup.unite.app.Profile;

import java.util.ArrayList;

/**
 * Created by bummy on 4/1/17.
 */

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType currentLayoutManagerType;
    protected RecyclerView recyclerView;
    protected HistoryAdapter historyAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        assignViews(rootView);
        assignVariables(savedInstanceState);
        assignHandlers();

        return rootView;
    }

    private void assignViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    private void assignVariables(Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getContext());
        Profile.INSTANCE.info.history.add("Anonymous Otter");
        Profile.INSTANCE.info.history.add("Anonymous Penguin");
        Profile.INSTANCE.info.history.add("Anonymous Zebra");
        Log.d(TAG, Profile.INSTANCE.info.history.size()+"");
        historyAdapter = new HistoryAdapter(new ArrayList<>(Profile.INSTANCE.info.history), Profile.INSTANCE.info.id);
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            currentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(currentLayoutManagerType);
        recyclerView.setAdapter(historyAdapter);
    }

    private void assignHandlers() {

    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(getContext());
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                layoutManager = new LinearLayoutManager(getContext());
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
