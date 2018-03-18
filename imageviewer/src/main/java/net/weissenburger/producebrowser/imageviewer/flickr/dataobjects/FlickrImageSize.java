package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public class FlickrImageSize implements Parcelable{
    String label;
    String width;
    String height;
    String source;
    String url;
    String media;

    protected FlickrImageSize(Parcel in) {
        label = in.readString();
        width = in.readString();
        height = in.readString();
        source = in.readString();
        url = in.readString();
        media = in.readString();
    }

    public static final Creator<FlickrImageSize> CREATOR = new Creator<FlickrImageSize>() {
        @Override
        public FlickrImageSize createFromParcel(Parcel in) {
            return new FlickrImageSize(in);
        }

        @Override
        public FlickrImageSize[] newArray(int size) {
            return new FlickrImageSize[size];
        }
    };

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return Integer.valueOf(width);
    }

    public int getHeight() {
        return Integer.valueOf(height);
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getMedia() {
        return media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static int returnSizeIndex(String size, FlickrImageSize[] sizes) {
        if (sizes == null)
            return -1;

        for (int i=0; i < sizes.length; i++) {
            if (sizes[i].getLabel().equals(size)) {
                return i;
            }
        }

        // not found
        return -1;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeString(width);
        parcel.writeString(height);
        parcel.writeString(source);
        parcel.writeString(url);
        parcel.writeString(media);
    }
}
