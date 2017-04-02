package com.powergroup.unite.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Picknick on 8/7/2016.
 */
public class VolleySingleton {
    private static VolleySingleton instance = null;
    private static Context volleyContext;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    // --------------------------------------------//--------------------------------------------
    private VolleySingleton(Context context) {
        volleyContext = context;
//		requestQueue = Volley.newRequestQueue(context, new OkHttpStack());

//		imageLoader = new ImageLoader(newRequestQueue(context),
//				new ImageLoader.ImageCache() {
//					private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
//							IMAGE_CACHING_SIZE);
//
//					public void putBitmap(String url, Bitmap bitmap) {
//						mCache.put(url, bitmap);
//					}
//
//					public Bitmap getBitmap(String url) {
//						return mCache.get(url);
//					}
//				});

//		requestQueue = Volley.newRequestQueue(context);

        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
//					private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
//							IMAGE_CACHING_SIZE);

                    private final BitmapLruCache mCache = new BitmapLruCache();

                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }
                });
    }

    // --------------------------------------------//--------------------------------------------
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    // --------------------------------------------//--------------------------------------------
    public RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            requestQueue = Volley.newRequestQueue(volleyContext.getApplicationContext(), new OkHttpStack());
        }
        return this.requestQueue;
    }

    // --------------------------------------------//--------------------------------------------
    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    // --------------------------------------------//--------------------------------------------
} // Ends VolleySingleton Class/