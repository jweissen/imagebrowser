package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

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

    int fullImageHeight;
    int fullImageWidth;


    protected FlickrImageItem(Parcel in) {
        previewImageUrl = in.readString();
        fullImageUrl = in.readString();
        caption = in.readString();
        imageId = in.readString();
        fullImageHeight = in.readInt();
        fullImageWidth = in.readInt();
        size = in.createTypedArray(FlickrImageSize.CREATOR);
    }

    public FlickrImageItem() {

    }

    public static final Creator<FlickrImageItem> CREATOR = new Creator<FlickrImageItem>() {
        @Override
        public FlickrImageItem createFromParcel(Parcel in) {
            return new FlickrImageItem(in);
        }

        @Override
        public FlickrImageItem[] newArray(int size) {
            return new FlickrImageItem[size];
        }
    };

    @Override
    public String getPreviewImageUrl() {
        if (previewImageUrl == null) {
            int index = FlickrImageSize.returnSizeIndex(sizeKeys.THUMBNAIL.getSize(), size);
            if (index >= 0)
                previewImageUrl = size[index].getSource();
        }

        return previewImageUrl;
    }

    @Override
    public String getFullImageUrl() {
        if (fullImageUrl == null) {

            int index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE.getSize(), size);
            if (index >= 0)
                fullImageUrl = size[index].getSource();
            else {
                index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE_FALLBACK.getSize(), size);
                if (index >= 0)
                    fullImageUrl = size[index].getSource();
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
    public void setFullImageHeight(int fullImageHeight) {
        this.fullImageHeight = fullImageHeight;
    }

    @Override
    public void setFullImageWidth(int fullImageWidth) {
        this.fullImageWidth = fullImageWidth;
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
    public int getFullImageHeight() {
        if (fullImageHeight == 0) {

            int index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE.getSize(), size);
            if (index >= 0)
                fullImageHeight = size[index].getHeight();
            else {
                index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE_FALLBACK.getSize(), size);
                if (index >= 0)
                    fullImageHeight = size[index].getHeight();
            }
        }
        return fullImageHeight;
    }

    @Override
    public int getFullImageWidth() {
        if (fullImageWidth == 0) {
            int index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE.getSize(), size);
            if (index >= 0)
                fullImageWidth = size[index].getWidth();
            else {
                index = FlickrImageSize.returnSizeIndex(sizeKeys.FULL_SIZE_FALLBACK.getSize(), size);
                if (index >= 0)
                    fullImageWidth = size[index].getWidth();
            }
        }
        return fullImageWidth;
    }

    @Override
    public IProduce deserialize(JSONObject jsonObject) throws JSONException {

        Gson gson = new Gson();
        return gson.fromJson(jsonObject.getJSONObject("sizes").toString(), FlickrImageItem.class);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(previewImageUrl);
        parcel.writeString(fullImageUrl);
        parcel.writeString(caption);
        parcel.writeString(imageId);
        parcel.writeInt(fullImageHeight);
        parcel.writeInt(fullImageWidth);
        parcel.writeTypedArray(size, i);
    }


    private enum sizeKeys {
        THUMBNAIL("Small"),
        FULL_SIZE("Large"),
        FULL_SIZE_FALLBACK("Original");

        private final String size;

        sizeKeys(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

    }


}
