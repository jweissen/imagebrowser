package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizeFilter implements IFlickrImageSizeFilter {

    @Override
    public int returnSizeIndex(String size, FlickrImageSize[] sizeArray) {
        if (sizeArray == null || size == null || size.equals(""))
            return -1;

        for (int i=0; i < sizeArray.length; i++) {
            if (sizeArray[i].getLabel().equals(size)) {
                return i;
            }
        }

        // not found
        return -1;
    }
}
