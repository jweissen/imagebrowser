package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("title")
    String caption;

    @SerializedName("id")
    String imageId;

    FlickrImageSize[] size;

    @Override
    public String getPreviewImageUrl() {
        if (previewImageUrl == null) {
            if (size == null) {
                return null;
            }

            for (FlickrImageSize s : size) {
                if (s.getLabel().equals(sizeKeys.THUMBNAIL.getSize())) {
                    previewImageUrl = s.getSource();
                }
            }
        }

        return previewImageUrl;
    }

    @Override
    public String getFullImageUrl() {
        if (fullImageUrl == null) {
            if (size == null) {
                return null;
            }

            for (FlickrImageSize s : size) {
                if (s.getLabel().equals(sizeKeys.FULL_SIZE.getSize())) {
                    fullImageUrl = s.getSource();
                }
            }
        }
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

        Gson gson = new Gson();
        return gson.fromJson(jsonObject.getJSONObject("sizes").toString(), FlickrImageItem.class);

    }

    private enum sizeKeys {
        THUMBNAIL("Large Square"),
        FULL_SIZE("Large");

        private final String size;

        sizeKeys(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

    }


}
