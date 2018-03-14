package net.weissenburger.producebrowser.imageviewer.flickr;

import com.google.gson.JsonParseException;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jon Weissenburger on 3/8/18.
 */

public class FlickrSearchResponse implements IProduceList {

    List<IProduce> list;

    public FlickrSearchResponse(List<IProduce> initialList) {
        list = initialList;
    }

    public FlickrSearchResponse() {
        list = new ArrayList<>();
    }

    @Override
    public List<IProduce> getProduce() {
        return list;
    }

    @Override
    public IProduceList deserialize(JSONObject jsonObject) throws JsonParseException, JSONException {

        jsonObject.get("photos");



        return null;
    }
}
