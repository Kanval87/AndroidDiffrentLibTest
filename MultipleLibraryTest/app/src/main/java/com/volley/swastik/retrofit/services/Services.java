package com.volley.swastik.retrofit.services;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.volley.swastik.App;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class Services {
    private static Services ourInstance;
    private final ServiceEndpoints endpoints;
    private X509TrustManager dummyTrustManager;
    private Retrofit secureRestAdapter;
    private Retrofit insecureRestAdapter;

    private Services() {
        Services.ourInstance = this;
        endpoints = new ServiceEndpoints();

        createDummyTrustManager();
        createRestAdapter();
    }

    public static Services getInstance() {
        if (ourInstance == null) {
            ourInstance = new Services();
        }
        return ourInstance;
    }

    public static ServiceEndpoints getEndpoints() {
        return getInstance().endpoints;
    }

    private OkHttpClient createClient() {
        int cacheSize = 4 * 1024 * 1024;
        File cacheDirectory = new File(App.getInstance().getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        OkHttpClient client = createOKHTTPClient();
        client.setCache(cache);
        return client;
    }

    private OkHttpClient createOKHTTPClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    dummyTrustManager
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createDummyTrustManager() {
        dummyTrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
    }

    public void createRestAdapter() {
        String apiEndpoint = App.getInstance().getApiEndpoint();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(createClient())
                .baseUrl(apiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));

        this.insecureRestAdapter = retrofitBuilder.build();
        this.secureRestAdapter = retrofitBuilder.build();

        this.secureRestAdapter.client().networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                if (!StringUtils.isEmpty(accessToken)) {
//                    Request.Builder requestBuilder = chain.request().newBuilder();
//                    Request request = requestBuilder.addHeader("Authorization", "Bearer " + accessToken).build();
//                    return chain.proceed(request);
//                } else {
//                    LightSailLog.v(TagUtil.shortFrom(this), "Secure call being made without user secret! This will probably not work.");
                return chain.proceed(chain.request());
//                }
            }
        });
    }

    public Retrofit getInsecureRestAdapter() {
        return insecureRestAdapter;
    }

    public Retrofit getSecureRestAdapter() {
        return secureRestAdapter;
    }
}
