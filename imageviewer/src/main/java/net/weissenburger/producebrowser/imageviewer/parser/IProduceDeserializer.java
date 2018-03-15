package net.weissenburger.producebrowser.imageviewer.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public interface IProduceDeserializer<T> {

    T deserialize(JSONObject jsonObject) throws JSONException;

}
