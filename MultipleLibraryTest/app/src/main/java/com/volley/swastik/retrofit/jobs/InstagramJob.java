package com.volley.swastik.retrofit.jobs;


import com.path.android.jobqueue.Params;
import com.volley.swastik.retrofit.jobs.future.Future;
import com.volley.swastik.retrofit.services.ServiceEndpoints;
import com.volley.swastik.retrofit.services.endPoint.InstagramEndPoint;

import java.io.IOException;

import retrofit.Call;

public class InstagramJob<ResultDataType> extends ServiceCallJob<ResultDataType> {

    private String tag;
    private String client_id;

    public InstagramJob(String tag, String client_id, Params params, Future<ResultDataType> future) {
        super(params, future);
        this.tag = tag;
        this.client_id = client_id;
    }

    @Override
    protected void execute() throws IOException {
        InstagramEndPoint.EndPoint endPoint = ServiceEndpoints.getInstance().getInstagramEndPoint();
        Call<Object> call = endPoint.getInstagramImages(tag, client_id);
        handleRetrofitCall(call);
    }


}
