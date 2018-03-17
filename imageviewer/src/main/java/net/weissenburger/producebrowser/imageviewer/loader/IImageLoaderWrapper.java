package net.weissenburger.producebrowser.imageviewer.loader;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Jon Weissenburger on 3/15/18.
 */

public interface IImageLoaderWrapper {

    ImageLoader unwrap(Context context);

}
