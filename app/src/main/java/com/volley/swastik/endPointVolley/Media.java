package com.volley.swastik.endPointVolley;

import com.android.volley.VolleyError;
import com.volley.swastik.App;
import com.volley.swastik.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import hugo.weaving.DebugLog;

/**
 * The name of the class and the packages structure reflects the
 * Instagram API structure (http://traning.example.instagramvolley.instagram.com/developer/endpoints/tags/)
 * The url end point and client id is declared on values/instagram_data.xml
 */


public class Media extends EndPoint<String> {
    private List<String> urlsCache;  //end point cache

    public Media(String tag) {
        super(tag);
        urlsCache = new ArrayList<String>();
    }

    @Override @DebugLog
    protected void prepareUrlString(String... tokens) {
        String client_id = App.getInstance().getResources().getString(R.string.instagram_client_id);
        nextUrlToLoad = App.getInstance().getResources().getString(R.string.instagram_api_tag_media);
        nextUrlToLoad = nextUrlToLoad.replace("{tag}", tokens[0]);
        nextUrlToLoad = nextUrlToLoad.replace("{client_id}", client_id);
    }

    @Override
    public int getDataCacheSize() {
        return urlsCache.size();
    }

    @Override
    public String getItemFromCache(int position) {
        return urlsCache.get(position);
    }

    @Override @DebugLog
    protected EndPointResult<List<String>> onLoadMoreDataSuccess(JSONObject jsonObject) throws JSONException {
        RestCallResult restCallResult = new RestCallResultBuilder(jsonObject).build();
        urlsCache.addAll(restCallResult.getData());
        nextUrlToLoad = restCallResult.getNextUrl();
        return restCallResult;
    }

    @Override
    protected int onLoadMoreDataFail(Exception exception) {
        if (exception instanceof JSONException) return 50;
        if (exception instanceof VolleyError) return 200;
        return 300;
    }

    @Override
    protected int onNetworkOffline() {
        return 2000;
    }
}
