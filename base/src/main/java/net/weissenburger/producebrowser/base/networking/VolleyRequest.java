package net.weissenburger.producebrowser.base.networking;

import com.android.volley.Request;
/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class VolleyRequest<T> implements IRequest<Request> {

    Request<T> request;

    private VolleyRequest(Request<T> request) {
        this.request = request;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    public static class VolleyRequestBuilder implements IRequestBuilder<Request> {
        @Override
        public IRequest createRequest(Request request) {
            return new VolleyRequest<>(request);
        }
    }
}
