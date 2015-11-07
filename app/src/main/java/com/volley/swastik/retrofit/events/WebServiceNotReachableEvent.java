package com.volley.swastik.retrofit.events;

import retrofit.Call;

public class WebServiceNotReachableEvent {
    private Call<?> call;

    public <ResultDataType> WebServiceNotReachableEvent(Call<?> call) {
        this.call = call;
    }

    public Call<?> getCall() {
        return call;
    }
}
