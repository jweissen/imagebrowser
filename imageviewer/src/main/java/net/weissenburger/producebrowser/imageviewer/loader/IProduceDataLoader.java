package net.weissenburger.producebrowser.imageviewer.loader;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduceDataLoader {
    void request(IProduceResponseCallback callback, IProduceDataAPI dataAPI, IProduceQuery query, IProduceResponseHandler responseHandler);
}
