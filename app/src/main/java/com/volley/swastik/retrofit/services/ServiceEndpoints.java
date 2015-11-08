package com.volley.swastik.retrofit.services;


import com.volley.swastik.retrofit.services.endPoint.InstagramEndPoint;

public class ServiceEndpoints {

    private static ServiceEndpoints serviceEndpoints;

    public static ServiceEndpoints getInstance() {
        if (serviceEndpoints == null) {
            serviceEndpoints = new ServiceEndpoints();
        }
        return serviceEndpoints;
    }

    private InstagramEndPoint instagramEndPoint;

    public InstagramEndPoint.EndPoint getInstagramEndPoint() {
        if (instagramEndPoint == null) {
            instagramEndPoint = new InstagramEndPoint();
        }
        return instagramEndPoint.getEndpoint();
    }
}
