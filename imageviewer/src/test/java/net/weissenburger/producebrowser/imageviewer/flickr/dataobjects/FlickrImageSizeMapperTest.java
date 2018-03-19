package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizeMapperTest {

    IFlickrImageSizeMapper mapper;
    IFlickrImageSizeFilter filter;

    @Before
    public void setup() {
        filter = mock(IFlickrImageSizeFilter.class);
        mapper = new FlickrImageSizeMapper(filter);
    }



}
