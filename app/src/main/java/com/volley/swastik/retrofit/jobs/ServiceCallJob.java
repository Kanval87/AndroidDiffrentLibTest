package com.volley.swastik.retrofit.jobs;

import com.path.android.jobqueue.Params;
import com.volley.swastik.retrofit.events.WebServiceNotReachableEvent;
import com.volley.swastik.retrofit.events.WebServiceReachedEvent;
import com.volley.swastik.retrofit.services.ServiceResponse;
import com.volley.swastik.enums.ServiceResponseTypeEnum;
import com.volley.swastik.retrofit.jobs.future.Future;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Response;

public abstract class ServiceCallJob<ResultDataType> extends PromiseJob<ResultDataType> {

    public ServiceCallJob(Params params) {
        super(params);
    }

    public ServiceCallJob(Params params, Future<ResultDataType> future) {
        super(params, future);
    }

    @Override
    protected abstract void execute() throws IOException;


    public ServiceResponse<ResultDataType> handleRetrofitCall(Call<?> call){
        int retriesLeft = 10;

        ServiceResponse<ResultDataType> returnValue = null;

        while (retriesLeft > 0) {
            try {
                Response<?> serviceModelResponse = call.execute();

                if (serviceModelResponse == null) {
                    returnValue = new ServiceResponse<>(null, ServiceResponseTypeEnum.NONE, 0);
                } else {
                    int responseCode = serviceModelResponse.code();
                    if (responseCode >= 200 && responseCode < 300) {
                        if (responseCode == 204) {
//                            returnValue = handleEventualConsistency(serviceModelResponse);
                        } else {
                            returnValue = new ServiceResponse<>(serviceModelResponse, ServiceResponseTypeEnum.SERVICE, responseCode);
                        }
                    } else {
                        returnValue = new ServiceResponse<>(serviceModelResponse, ServiceResponseTypeEnum.SERVICE, responseCode);
                    }
                }
            } catch (IOException e) {
                returnValue = new ServiceResponse<>(null, ServiceResponseTypeEnum.NONE, 0);
                EventBus.getDefault().post(new WebServiceNotReachableEvent(call));
            }

            if (returnValue != null) {
                if (returnValue.isInternetError()) {
                    retriesLeft--;
                    waitForRetry();
                } else {
                    break;
                }
            } else {
                retriesLeft--;
                waitForRetry();
            }
        }

        if (returnValue != null && !returnValue.isInternetError()) {
            EventBus.getDefault().post(new WebServiceReachedEvent(call));
        }

        return returnValue;
    }

    private void waitForRetry() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
//            LightSailLog.e(TagUtil.shortFrom(this), "Retrying call due to internet being unavailable suspended!", e);
        }
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
