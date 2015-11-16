package com.ble.a1.bledemo;


import android.annotation.TargetApi;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<ScanResult> stringList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<ScanResult> stringList) {
        this.stringList = stringList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        //Setting text view title
        customViewHolder.textView.setText(stringList.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return (null != stringList ? stringList.size() : 0);
    }

}