package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public interface IFlickrImageSizeFilter {
    int returnSizeIndex(String size, FlickrImageSize[] sizeArray);

}
