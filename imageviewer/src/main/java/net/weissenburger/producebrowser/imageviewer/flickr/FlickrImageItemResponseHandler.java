package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseHandler;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.parser.IProduceDeserializer;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public class FlickrImageItemResponseHandler implements IProduceResponseHandler {

    IProduceDeserializer<IProduce> deserializer;

    public FlickrImageItemResponseHandler(IProduceDeserializer<IProduce> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void handleResponse(IProduceResponseCallback callback, JSONObject response) {
        IProduce item = null;

        try {
            item = deserializer.deserialize(response);
        } catch (JSONException e) {
            callback.onError(IProduceResponseCallback.ErrorCode.UNKNOWN, e.getMessage());
        }

        // no data found for request
        if (item == null) {
            callback.onError(IProduceResponseCallback.ErrorCode.NO_RESULTS, "No Results Found");
            return;
        }

        callback.onResponse(item);
    }

    @Override
    public void handleError(IProduceResponseCallback callback, String errorMessage) {
        callback.onError(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE, errorMessage);
    }

}
