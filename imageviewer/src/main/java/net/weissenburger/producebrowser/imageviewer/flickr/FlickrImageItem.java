package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.parser.IProduceDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public class FlickrImageItem implements IProduce, IProduceDeserializer<IProduce> {

    String previewImageUrl;
    String fullImageUrl;
    String caption;
    String imageId;

    public FlickrImageItem(){

    }

    @Override
    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    @Override
    public String getFullImageUrl() {
        return fullImageUrl;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getImageId() {
        return imageId;
    }

    @Override
    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;

    }

    @Override
    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public IProduce deserialize(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
