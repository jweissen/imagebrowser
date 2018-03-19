package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizeMapper implements IFlickrImageSizeMapper {

    IFlickrImageSizeFilter filter;

    public FlickrImageSizeMapper(IFlickrImageSizeFilter filter) {
        this.filter = filter;
    }

    @Override
    public String getFullImageUrl(String size, String fallbackSize, FlickrImageSize[] array) {
        String fullImageUrl = null;

        int index = filter.returnSizeIndex(FlickrImageItem.sizeKeys.FULL_SIZE.getSize(), array);
        if (index >= 0)
            fullImageUrl = array[index].getSource();
        else {
            index = filter.returnSizeIndex(FlickrImageItem.sizeKeys.FULL_SIZE_FALLBACK.getSize(), array);
            if (index >= 0)
                fullImageUrl = array[index].getSource();
        }

        return fullImageUrl;
    }

    @Override
    public String getPreviewImageUrl(String size, FlickrImageSize[] array) {
        String previewImageUrl = null;
        int index = filter.returnSizeIndex(size, array);
        if (index >= 0)
            previewImageUrl = array[index].getSource();

        return previewImageUrl;
    }

    @Override
    public int getFullImageHeight(String size, String fallbackSize, FlickrImageSize[] array) {
        int fullImageHeight = 0;

        int index = filter.returnSizeIndex(size, array);
        if (index >= 0)
            fullImageHeight = array[index].getHeight();
        else {
            index = filter.returnSizeIndex(fallbackSize, array);
            if (index >= 0)
                fullImageHeight = array[index].getHeight();
        }

        return fullImageHeight;
    }

    @Override
    public int getFullImageWidth(String size, String fallbackSize, FlickrImageSize[] array) {

        int fullImageWidth = 0;

        int index = filter.returnSizeIndex(size, array);
        if (index >= 0)
            fullImageWidth = array[index].getWidth();
        else {
            index = filter.returnSizeIndex(FlickrImageItem.sizeKeys.FULL_SIZE_FALLBACK.getSize(), array);
            if (index >= 0)
                fullImageWidth = array[index].getWidth();
        }
        return fullImageWidth;
    }
}
