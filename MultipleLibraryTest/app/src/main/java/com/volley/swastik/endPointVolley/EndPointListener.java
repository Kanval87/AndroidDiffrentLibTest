package com.volley.swastik.endPointVolley;

public interface EndPointListener {
    public void onNetworkOffline();
    public void onStartLoading();
    public void onLoadMoreDataSuccess(EndPointResult endPointResult);
    public void onLoadingMoreDataFail(Exception exception);
    public void onInstagramNotResponding();
}
