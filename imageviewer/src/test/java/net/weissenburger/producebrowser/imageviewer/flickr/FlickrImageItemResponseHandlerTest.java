package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseHandler;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;
import net.weissenburger.producebrowser.imageviewer.parser.IProduceDeserializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public class FlickrImageItemResponseHandlerTest {
    IProduceResponseHandler handler;
    IProduceDeserializer deserializer;
    IProduce item;

    @Before
    public void setup() {
        deserializer = mock(IProduceDeserializer.class);
        item = mock(IProduce.class);

        handler = new FlickrImageItemResponseHandler(deserializer);
    }


    @Test
    public void testItemNull() throws JSONException {

        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        JSONObject response = mock(JSONObject.class);

        // test null
        when(deserializer.deserialize(response)).thenReturn(null);
        handler.handleResponse(callback, response);
        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.NO_RESULTS), anyString());

    }

    @Test
    public void testResultsReturned() throws JSONException {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        JSONObject response = mock(JSONObject.class);

        when(deserializer.deserialize(response)).thenReturn(item);
        handler.handleResponse(callback, response);
        verify(callback).onResponse(item);
    }

    @Test
    public void testBadResponseData() throws JSONException {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        JSONObject response = mock(JSONObject.class);

        when(deserializer.deserialize(response)).thenThrow(new JSONException("Parse error!"));
        handler.handleResponse(callback, response);
        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.UNKNOWN), Mockito.<String>any());
    }

}
