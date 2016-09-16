package com.buenadigital.saaspro.services;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.buenadigital.saaspro.SaasProApplication;


public class VolleyServiceSingleton {

    private static String mSessionCode = "";

    private static VolleyServiceSingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyServiceSingleton(){
        mRequestQueue = Volley.newRequestQueue(SaasProApplication.getSaaSContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache(){
            int memClass = ( (ActivityManager)SaasProApplication.getSaaSContext().getSystemService( Context.ACTIVITY_SERVICE ) ).getMemoryClass();
            int cacheSize = 1024 * 1024 * memClass / 8;
            private LruCache<String, Bitmap> mImageCache = new LruCache<>(cacheSize);
            @Override
            public Bitmap getBitmap(String url) {
                return mImageCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mImageCache.put(url, bitmap);
            }
        });
    }

    public static VolleyServiceSingleton getInstance(){
        if(mInstance==null){
            mInstance = new VolleyServiceSingleton();

        }
        return mInstance;
    }

    public RequestQueue gerRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }



}
