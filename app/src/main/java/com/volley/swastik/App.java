package com.volley.swastik;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class App extends Application {
    private static App instance;

    private WeakReference<Activity> foregroundActivity;

    public String getApiEndpoint() {
        return "https://api.instagram.com";
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Point getScreenSize() {
        WindowManager windowManager = (WindowManager) instance.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public Activity getForegroundActivity() {
        return foregroundActivity != null ? foregroundActivity.get() : null;
    }

    public void setForegroundActivity(Activity foregroundActivity) {
        this.foregroundActivity = new WeakReference<>(foregroundActivity);
    }
}
