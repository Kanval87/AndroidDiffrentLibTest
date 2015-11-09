package com.volley.swastik.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import hugo.weaving.DebugLog;

@DebugLog
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private static BitmapCache instance;

    private BitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
        instance =  this;
    }

    public static BitmapCache getInstance(){
        return getInstance(getDefaultLruCacheSize());
    }

    public static BitmapCache getInstance(int sizeInKiloBytes){
        if(instance == null) new BitmapCache(sizeInKiloBytes);
        return instance;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected void entryRemoved(boolean evicted, String url, Bitmap oldBitmap, Bitmap newBitmap){
        oldBitmap.recycle(); //very important to avoid out of memory exception
    }

    public static int getDefaultLruCacheSize() {
        return (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
    }
}