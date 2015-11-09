package com.volley.swastik.endPointVolley;

import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.volley.swastik.App;
import com.volley.swastik.R;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Base to create representations of the instagram api end points.
 * This is designed so the sub classes can be used as a data source
 * for a custom adapter.
 * The subclasses must implement a data structure to hold a cache
 * of the end point. The data structure should support random access,
 * so LinkedList is not recommended.
 * Uses the Observer pattern to propagate events to the EndPointListener
 *
 * @param <ItemType> Type to be returned by the getItemFromCache method
 */
@DebugLog
public abstract class EndPoint<ItemType> implements Response.ErrorListener, Response.Listener<JSONObject> {
    protected RequestQueue requestQueue;
    protected String nextUrlToLoad;
    protected int retryCount;
    protected int retryLimit;
    protected boolean loading;

    protected List<EndPointListener> listeners;

    public EndPoint(String token) {
        prepareUrlString(token);
        listeners = new LinkedList<EndPointListener>();
        requestQueue = InstagramRequestQueueBuilder.getRequestQueue();
        retryCount = 0;
        retryLimit = App.getInstance().getResources().getInteger(R.integer.instagram_retry_limit);
    }

    protected abstract void prepareUrlString(String... tokens);

    public abstract int getDataCacheSize();

    public abstract ItemType getItemFromCache(int position);

    ///////////////////// Load more data logic

    protected abstract int onNetworkOffline();

    public void loadMoreData() {
        if (loading || retryIfNetworkOffline()) return;

        loading = true;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, nextUrlToLoad, this, this);
        dispatchOnStartLoading();
        requestQueue.add(request);
    }

    private boolean retryIfNetworkOffline() {
        if (!App.isOnline()) {
            int timeToRetry = onNetworkOffline();
            dispatchOnNetworkOffline();
            retry(timeToRetry);
            return true;
        }
        return false;
    }

    ///////////////////// Volley call backs implementation

    protected abstract EndPointResult onLoadMoreDataSuccess(JSONObject jsonObject) throws Exception;

    protected abstract int onLoadMoreDataFail(Exception exception);

    @Override
    public void onResponse(JSONObject jsonObject) {
        loading = false;
        try {
            retryCount = 0;
            EndPointResult endPointResult = onLoadMoreDataSuccess(jsonObject);
            dispatchOnLoadMoreDataSuccess(endPointResult); //dispatchOnLoadMoreDataSuccess logic extracted from this method because of clean code
        } catch (Exception exception) {
            int timeToRetry = onLoadMoreDataFail(exception);

            dispatchOnLoadMoreDataFail(exception); //dispatchOnLoadMoreDataFail logic extracted from this method because of clean code
            if (retriesLimitsReached())
                return; //retriesLimitsReached logic extracted from this method because of clean code

            if (timeToRetry > -1)
                retry(timeToRetry); //retry logic extracted from this method because of clean code
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        loading = false;
        int timeToRetry = onLoadMoreDataFail(volleyError);

        dispatchOnLoadMoreDataFail(volleyError); //dispatchOnLoadMoreDataFail logic extracted from this method because of clean code
        if (retriesLimitsReached())
            return; //retriesLimitsReached logic extracted from this method because of clean code

        if (timeToRetry > -1)
            retry(timeToRetry); //retry logic extracted from this method because of clean code
    }

    ///////////////////// EVENTS

    public void addListener(EndPointListener endPointListener) {
        listeners.add(endPointListener);
    }

    public void removeListener(EndPointListener endPointListener) {
        listeners.remove(endPointListener);
    }

    protected void dispatchOnLoadMoreDataFail(Exception exception) {
        for (EndPointListener listener : listeners)
            listener.onLoadingMoreDataFail(exception);
    }

    protected void dispatchOnLoadMoreDataSuccess(EndPointResult endPointResult) {
        for (EndPointListener listener : listeners)
            listener.onLoadMoreDataSuccess(endPointResult);
    }

    protected void dispatchOnNetworkOffline() {
        for (EndPointListener listener : listeners)
            listener.onNetworkOffline();
    }

    protected void dispatchOnStartLoading() {
        for (EndPointListener listener : listeners)
            listener.onStartLoading();
    }

    protected void dispatchOnInstagramNotResponding() {
        for (EndPointListener listener : listeners)
            listener.onInstagramNotResponding();
    }

    ///////////////////// Retry

    private boolean retriesLimitsReached() {
        if (retryCount == retryLimit) {
            dispatchOnInstagramNotResponding();
            return true;
        }
        return false;
    }

    private void retry(long millisecondsToRetry) {
        retryCount++;
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData();
                    }
                },
                millisecondsToRetry
        );
    }
}
