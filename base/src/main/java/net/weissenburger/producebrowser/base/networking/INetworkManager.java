package net.weissenburger.producebrowser.base.networking;

import android.content.Context;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public interface INetworkManager<T, U, V>{

    U getRequestQueue();
    void addToRequestQueue(IRequest<T> req);
    V getImageLoader();

    interface INetworkManagerBuilder<T extends INetworkManager> {
        T getInstance(Context context);
    }
}
