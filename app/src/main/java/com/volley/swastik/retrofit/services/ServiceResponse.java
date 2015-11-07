package com.volley.swastik.retrofit.services;


import com.volley.swastik.enums.ServiceResponseTypeEnum;

import retrofit.Response;

public class ServiceResponse<ServiceModel> {
    private final Response<ServiceModel> serviceResponse;
    private final ServiceResponseTypeEnum responseType;
    private final int responseCode;

    public ServiceResponse(Response<ServiceModel> serviceModelResponse, ServiceResponseTypeEnum responseType, int responseCode) {
        this.serviceResponse = serviceModelResponse;
        this.responseType = responseType;
        this.responseCode = responseCode;
    }

    public Response<ServiceModel> getServiceResponse() {
        return serviceResponse;
    }

    public ServiceResponseTypeEnum getResponseType() {
        return responseType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isServerError() {
        return (!(this.getResponseCode() >= 200 && this.getResponseCode() < 300)) && (this.responseType != ServiceResponseTypeEnum.NONE);
    }

    public boolean isInternetError() {
        return (this.responseType == ServiceResponseTypeEnum.NONE);
    }
}
