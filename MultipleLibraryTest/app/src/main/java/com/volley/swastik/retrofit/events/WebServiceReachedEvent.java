package com.volley.swastik.retrofit.events;

import retrofit.Call;

public class WebServiceReachedEvent {
    private Call<?> call;

    public <ResultDataType> WebServiceReachedEvent(Call<?> call) {
        this.call = call;
    }

    public Call<?> getCall() {
        return call;
    }
}
