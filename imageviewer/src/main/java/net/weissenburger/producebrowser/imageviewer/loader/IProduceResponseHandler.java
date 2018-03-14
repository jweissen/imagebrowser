package net.weissenburger.producebrowser.imageviewer.loader;

import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public interface IProduceResponseHandler {

    void handleResponse(IProduceResponseCallback callback, JSONObject response);
    void handleError(IProduceResponseCallback callback, String errorMessage);

}
