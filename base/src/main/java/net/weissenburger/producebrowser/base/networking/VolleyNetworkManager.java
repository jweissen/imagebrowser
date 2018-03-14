package net.weissenburger.producebrowser.base.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class VolleyNetworkManager implements INetworkManager<Request, RequestQueue, ImageLoader> {

    private static VolleyNetworkManager instance;
    private static Context context;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private VolleyNetworkManager(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

    }


    @Override
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    @Override
    public void addToRequestQueue(IRequest<Request> req) {
        getRequestQueue().add(req.getRequest());
    }

    @Override
    public ImageLoader getImageLoader() {
        return null;
    }


    public static class VolleyNetworkManagerBuilder implements INetworkManagerBuilder<VolleyNetworkManager> {

        @Override
        public VolleyNetworkManager getInstance(Context context) {
            if (instance == null) {
                instance = new VolleyNetworkManager(context);
            }

            return instance;
        }
    }

}
