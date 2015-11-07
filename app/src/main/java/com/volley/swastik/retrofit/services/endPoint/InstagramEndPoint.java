package com.volley.swastik.retrofit.services.endPoint;

import com.volley.swastik.retrofit.services.Services;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class InstagramEndPoint {
    private final EndPoint endpoint;

    public InstagramEndPoint() {
        this.endpoint = Services.getInstance().getInsecureRestAdapter().create(EndPoint.class);
    }

    public EndPoint getEndpoint() {
        return endpoint;
    }

    public interface EndPoint {
        @GET("/v1/tags/{tag}/media/recent")
        Call<Object> getInstagramImages(@Path("tag") String tag, @Query("client_id") String client_id);
    }

}
