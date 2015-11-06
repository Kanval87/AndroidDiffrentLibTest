package com.volley.swastik.retrofitJob.future;


import com.volley.swastik.endPointRetrofit.services.ServiceResponse;

public abstract class Future<T> {
    private boolean pending = true;

    public void onCallCompleted(T value) {
    }

    public void onCallPending(int eventualConsistencyAttemptsLeft) {
    }

    public void onCallFailed(ServiceResponse<T> value) {
    }

    public void onServerUnreachable() {
    }

    public final void cancel() {
        pending = false;
    }

    public final boolean isPending() {
        return pending;
    }
}
