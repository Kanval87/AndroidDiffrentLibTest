package com.ble.a1.bledemo;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    protected TextView textView;

    public CustomViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.title);
    }
}