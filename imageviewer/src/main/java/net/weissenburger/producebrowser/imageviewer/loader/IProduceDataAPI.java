package net.weissenburger.producebrowser.imageviewer.loader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public interface IProduceDataAPI {

    String getURL(IProduceQuery query) throws UnsupportedEncodingException;

}
