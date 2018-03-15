package net.weissenburger.producebrowser.imageviewer.loader;

import android.content.Context;

import net.weissenburger.producebrowser.base.networking.IJsonRequestWrapper;
import net.weissenburger.producebrowser.base.networking.INetworkManager;
import net.weissenburger.producebrowser.base.networking.IRequest;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class ProduceDataLoaderTest {

    IProduceDataLoader loader;
    Context context;
    INetworkManager.INetworkManagerBuilder networkManagerBuilder;
    IRequest.IRequestBuilder requestBuilder;
    IJsonRequestWrapper requestWrapper;


    @Before
    public void setup() {
        context = mock(Context.class);
        networkManagerBuilder = mock(INetworkManager.INetworkManagerBuilder.class);
        requestBuilder = mock(IRequest.IRequestBuilder.class);
        requestWrapper = mock(IJsonRequestWrapper.class);

        loader = new ProduceDataLoader(context, networkManagerBuilder, requestBuilder, requestWrapper);
    }

    @Test
    public void testNullQueryString() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceResponseHandler responseHandler = mock(IProduceResponseHandler.class);

        when(query.getQuery()).thenReturn(null);

        loader.request(callback, dataAPI, query, responseHandler);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }

    @Test
    public void testNullQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = null;
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceResponseHandler responseHandler = mock(IProduceResponseHandler.class);

        loader.request(callback, dataAPI, query, responseHandler);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }

    @Test
    public void testEmptyQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceResponseHandler responseHandler = mock(IProduceResponseHandler.class);

        when(query.getQuery()).thenReturn("");

        loader.request(callback, dataAPI, query, responseHandler);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }

    @Test
    public void testWithQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);
        INetworkManager manager = mock(INetworkManager.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceResponseHandler responseHandler = mock(IProduceResponseHandler.class);

        when(query.getQuery()).thenReturn("tomatoes");
        when(networkManagerBuilder.getInstance(context)).thenReturn(manager);
        when(requestBuilder.createRequest(any())).thenReturn(mock(IRequest.class));

        loader.request(callback, dataAPI, query, responseHandler);

        // validate request was added to queue
        verify(networkManagerBuilder.getInstance(context)).addToRequestQueue(requestBuilder.createRequest(any()));
    }

    @Test
    public void testWithNullAPI() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);
        IProduceDataAPI dataAPI = null;
        IProduceResponseHandler responseHandler = mock(IProduceResponseHandler.class);

        when(query.getQuery()).thenReturn("tomatoes");

        loader.request(callback, dataAPI, query, responseHandler);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_API_REQUEST), anyString());
    }


}
