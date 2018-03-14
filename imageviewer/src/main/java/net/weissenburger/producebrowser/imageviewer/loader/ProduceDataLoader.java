package net.weissenburger.producebrowser.imageviewer.loader;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;

import net.weissenburger.producebrowser.base.networking.IJsonRequestWrapper;
import net.weissenburger.producebrowser.base.networking.INetworkManager;
import net.weissenburger.producebrowser.base.networking.IRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public class ProduceDataLoader implements IProduceDataLoader {

    Context context;
    INetworkManager.INetworkManagerBuilder networkBuilder;
    IRequest.IRequestBuilder<Request> requestBuilder;
    IJsonRequestWrapper jsonRequestWrapper;
    IProduceResponseHandler responseHandler;

    public ProduceDataLoader(Context context,
                                  INetworkManager.INetworkManagerBuilder networkBuilder,
                                  IRequest.IRequestBuilder requestBuilder,
                                  IProduceResponseHandler handler,
                                  IJsonRequestWrapper requestWrapper) {
        this.context = context;
        this.networkBuilder = networkBuilder;
        this.requestBuilder = requestBuilder;
        this.jsonRequestWrapper = requestWrapper;
        this.responseHandler = handler;
    }

    @Override
    public void request(final IProduceResponseCallback callback, IProduceDataAPI dataAPI, IProduceQuery query) {

        if (query == null || query.getQuery() == null || query.getQuery().isEmpty()) {
            callback.onError(IProduceResponseCallback.ErrorCode.BAD_QUERY, "Query was empty");
            return;
        }

        if (dataAPI == null) {
            callback.onError(IProduceResponseCallback.ErrorCode.BAD_API_REQUEST, "Data API can not be null for request");
            return;
        }

        JsonRequest request;

        try {
            request = jsonRequestWrapper.getJsonObjectRequest(Request.Method.GET, dataAPI.getURL(query), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            responseHandler.handleResponse(callback, response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseHandler.handleError(callback, error.getMessage());
                        }
                    });

        } catch (UnsupportedEncodingException e) {
            callback.onError(IProduceResponseCallback.ErrorCode.BAD_QUERY, "Unable to encode query");
            return;
        }


        networkBuilder.getInstance(context).addToRequestQueue(requestBuilder.createRequest(request));

    }




}
