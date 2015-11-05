package com.volley.swastik.endPointVolley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.volley.swastik.App;


/**
 * Singleton Builder for an instance of a volley request queue.
 * This combine the singleton and builder design pattern
 */
public class InstagramRequestQueueBuilder {
    private static RequestQueue requestQueue;

    private static void build() {
        requestQueue = Volley.newRequestQueue(App.getInstance().getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) build();
        return requestQueue;
    }
}
