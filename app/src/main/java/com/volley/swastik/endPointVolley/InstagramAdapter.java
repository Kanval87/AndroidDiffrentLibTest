package com.volley.swastik.endPointVolley;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.volley.swastik.App;

import hugo.weaving.DebugLog;

/**
 * Custom adapter designed to work with the EndPoint subclasses
 */

public class InstagramAdapter extends BaseAdapter implements EndPointListener {
    private Point screenSize;
    private EndPoint<String> endPoint;
    private Context context;

    public InstagramAdapter(EndPoint<String> endPoint, Context context) {
        this.endPoint = endPoint;
        endPoint.addListener(this);
        if (endPoint.getDataCacheSize() == 0) endPoint.loadMoreData();
        this.context = context;
        screenSize = App.getScreenSize();
    }

    @Override
    public int getCount() {
        return endPoint.getDataCacheSize();
    }

    @Override
    @DebugLog
    public String getItem(int i) {
        return endPoint.getItemFromCache(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NetworkImageView networkImageView;
        if (view == null)
            networkImageView = buildNetworkImageView(); //buildNetworkImageView logic extracted from this method because of clean code
        else networkImageView = (NetworkImageView) view;
        networkImageView.setImageUrl(getItem(i), InstagramImageLoaderBuilder.getImageLoader());
        return networkImageView;
    }

    private NetworkImageView buildNetworkImageView() {
        NetworkImageView networkImageView = new NetworkImageView(context);
        networkImageView.setMinimumHeight(screenSize.x);
        networkImageView.setMinimumWidth(screenSize.x);
        return networkImageView;
    }

    @Override
    public void onNetworkOffline() {
    }

    @Override
    public void onStartLoading() {
    }

    @Override
    public void onLoadMoreDataSuccess(EndPointResult endPointResult) {
        notifyDataSetChanged();
    }

    @Override
    public void onLoadingMoreDataFail(Exception exception) {
    }

    @Override
    public void onInstagramNotResponding() {
    }

    public void dispose() {
        this.endPoint.removeListener(this);
        this.endPoint = null;
        this.context = null;
    }

    @DebugLog
    public void loadMoreData() {
        endPoint.loadMoreData();
    }
}
