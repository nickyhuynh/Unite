package com.powergroup.unite.history;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powergroup.unite.R;
import com.powergroup.unite.dto.Message;
import com.powergroup.unite.main.MainActivity;
import com.powergroup.unite.qr.QRActivity;

import java.util.ArrayList;

/**
 * Created by Nicky on 2/2/2017.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final String TAG = "HistoryAdapter";

    private ArrayList<String> dataSet;

    private String owner;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        private TextView username;
        private LinearLayout qr_scan;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;

            username = (TextView) v.findViewById(R.id.username);
            qr_scan = (LinearLayout) v.findViewById(R.id.qr_scan);
        }
    }

    public HistoryAdapter(ArrayList<String> dataSet, String owner) {
        Log.d(TAG, dataSet.size()+"");
        this.dataSet = dataSet;
        this.owner = owner;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_history, parent, false);
        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)parent.getContext()).navigateToChat(owner, "345345354");
            }
        });

        vh.qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new com.google.zxing.integration.android.IntentIntegrator((MainActivity)parent.getContext()).initiateScan();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String user = dataSet.get(position);

        holder.username.setText(user);

        if(position == dataSet.size()-1) {
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 0, 0, 0);
            holder.cardView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setDataSet(ArrayList<String> history) {
        this.dataSet = history;
    }

    public String getItem(int position) {
        return dataSet.get(position);
    }

}
