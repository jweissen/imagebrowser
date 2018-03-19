package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizeFilterTest {

    IFlickrImageSizeFilter filter;
    FlickrImageSize[] array;
    FlickrImageSize size;

    @Before
    public void setup() {
        filter = new FlickrImageSizeFilter();
        array = new FlickrImageSize[1];
        size = mock(FlickrImageSize.class);

        array[0] = size;
    }

    @Test
    public void testWithNullArray() {
        int index = filter.returnSizeIndex("large", null);
        assertTrue(index == -1);
    }

    @Test
    public void testWithEmptyAndNullString() {
        int index = filter.returnSizeIndex(null, array);
        assertTrue(index == -1);

        index = filter.returnSizeIndex("", array);
        assertTrue(index == -1);
    }

    @Test
    public void testMatch() {
        String matchingLabel = "large";

        when(size.getLabel()).thenReturn(matchingLabel);
        int index = filter.returnSizeIndex(matchingLabel, array);
        assertTrue(index == 0);

    }

}
