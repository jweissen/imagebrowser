package net.weissenburger.producebrowser.imageviewer.flickr;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;
import net.weissenburger.producebrowser.imageviewer.parser.IProduceDeserializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jon Weissenburger on 3/8/18.
 */

public class FlickrSearchResponse implements IProduceList, IProduceDeserializer<IProduceList> {

    List<IProduce> list;
    int page;
    int pages;
    int perpage;
    String total;


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
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.getJSONObject("photos").toString(), FlickrSearchResponse.class);
    }
}
