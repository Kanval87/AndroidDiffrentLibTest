package com.volley.swastik.endPointVolley;

import com.android.volley.toolbox.ImageLoader;
import com.volley.swastik.utils.BitmapCache;


/**
 * Singleton Builder for an instance of a volley image loader.
 * This combine the singleton and builder design pattern
 */
public class InstagramImageLoaderBuilder {
    private static ImageLoader imageLoader;

    private static void build(){
        imageLoader = new ImageLoader(InstagramRequestQueueBuilder.getRequestQueue(), BitmapCache.getInstance());
    }

    public static ImageLoader getImageLoader(){
        if(imageLoader == null) build();
        return imageLoader;
    }
}
