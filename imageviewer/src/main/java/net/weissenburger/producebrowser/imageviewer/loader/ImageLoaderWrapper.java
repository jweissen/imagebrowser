package net.weissenburger.producebrowser.imageviewer.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

import net.weissenburger.producebrowser.base.networking.VolleyNetworkManager;

/**
 * Created by Jon Weissenburger on 3/15/18.
 */

public class ImageLoaderWrapper implements IImageLoaderWrapper {

    ImageLoader imageLoader;

    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;

    @Override
    public ImageLoader unwrap(Context context) {

        if (imageLoader == null) {

            imageLoader = new ImageLoader(new VolleyNetworkManager.VolleyNetworkManagerBuilder().getInstance(context).getRequestQueue(), new ImageLoader.ImageCache() {
                private final android.support.v4.util.LruCache<String, Bitmap> mCache = new android.support.v4.util.LruCache<String, Bitmap>(cacheSize) {
                    @Override
                    protected int sizeOf(String key, Bitmap value) {
                        return value.getByteCount() / 1024;
                    }
                };

                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url, bitmap);
                }

                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }
            });
        }

        return imageLoader;
    }
}
