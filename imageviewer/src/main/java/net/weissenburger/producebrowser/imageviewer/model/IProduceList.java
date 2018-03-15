package net.weissenburger.producebrowser.imageviewer.model;

import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduceList {

    List<IProduce> getProduce();
}
