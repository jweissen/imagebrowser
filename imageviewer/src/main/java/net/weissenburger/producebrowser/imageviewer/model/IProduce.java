package net.weissenburger.producebrowser.imageviewer.model;

import android.os.Parcelable;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduce extends Parcelable {

    String getPreviewImageUrl();
    String getFullImageUrl();
    String getCaption();
    String getImageId();
    void setPreviewImageUrl(String previewImageUrl);
    void setFullImageUrl(String fullImageUrl);
    void setFullImageHeight(int fullImageHeight);
    void setFullImageWidth(int fullImageWidth);
    void setCaption(String caption);
    void setImageId(String imageId);
    int getFullImageHeight();
    int getFullImageWidth();

}
