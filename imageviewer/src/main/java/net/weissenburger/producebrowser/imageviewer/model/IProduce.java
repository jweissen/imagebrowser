package net.weissenburger.producebrowser.imageviewer.model;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduce {

    String getPreviewImageUrl();
    String getFullImageUrl();
    String getCaption();
    String getImageId();
    void setPreviewImageUrl(String previewImageUrl);
    void setFullImageUrl(String fullImageUrl);
    void setCaption(String caption);
    void setImageId(String imageId);

}
