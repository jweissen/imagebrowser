package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public interface IFlickrImageSizeMapper {

    String getFullImageUrl(String size, String fallbackSize, FlickrImageSize[] array);
    String getPreviewImageUrl(String size, FlickrImageSize[] array);
    int getFullImageHeight(String size, String fallbackSize, FlickrImageSize[] array);
    int getFullImageWidth(String size, String fallbackSize, FlickrImageSize[] array);

}
