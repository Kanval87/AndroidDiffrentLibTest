package com.volley.swastik.endPointRetrofit.services;


import com.volley.swastik.endPointRetrofit.services.endPoint.InstagramEndPoint;

public class ServiceEndpoints {

    private static ServiceEndpoints serviceEndpoints;

    public static ServiceEndpoints getInstance() {
        if (serviceEndpoints == null) {
            serviceEndpoints = new ServiceEndpoints();
        }
        return serviceEndpoints;
    }

    private InstagramEndPoint instagramEndPoint;

    private InstagramEndPoint.EndPoint getInstagramEndPoint() {
        if (instagramEndPoint == null) {
            instagramEndPoint = new InstagramEndPoint();
        }
        return instagramEndPoint.getEndpoint();
    }
}
