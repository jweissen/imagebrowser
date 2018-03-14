package net.weissenburger.producebrowser.base.networking;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public interface IRequest<T> {

    T getRequest();

    interface IRequestBuilder<T> {
        IRequest createRequest(T request);
    }


}
