package net.weissenburger.producebrowser.base.networking;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public interface IJsonRequestWrapper {

    JsonObjectRequest getJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);


}
