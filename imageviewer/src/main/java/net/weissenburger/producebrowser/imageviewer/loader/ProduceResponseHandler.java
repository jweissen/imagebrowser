package net.weissenburger.producebrowser.imageviewer.loader;

import com.google.gson.JsonParseException;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class ProduceResponseHandler implements IProduceResponseHandler {

    IProduceList produceList;

    public ProduceResponseHandler(IProduceList list) {
        this.produceList = list;
    }

    @Override
    public void handleResponse(IProduceResponseCallback callback, JSONObject response) {

        IProduceList list = null;

        try {
            list = produceList.deserialize(response);
        } catch (JSONException e) {
            callback.onError(IProduceResponseCallback.ErrorCode.UNKNOWN, e.getMessage());
        }

        // no data found for request
        if (list == null || list.getProduce() == null || list.getProduce().isEmpty()) {
            callback.onError(IProduceResponseCallback.ErrorCode.NO_RESULTS, "No Results Found");
            return;
        }

        callback.onResponse(list.getProduce().toArray(new IProduce[list.getProduce().size()]));
    }

    @Override
    public void handleError(IProduceResponseCallback callback, String errorMessage) {
        callback.onError(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE, errorMessage);
    }

}
