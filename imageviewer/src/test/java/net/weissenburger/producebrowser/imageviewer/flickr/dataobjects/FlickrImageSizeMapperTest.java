package net.weissenburger.producebrowser.imageviewer.flickr.dataobjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizeMapperTest {

    IFlickrImageSizeMapper mapper;
    IFlickrImageSizeFilter filter;

    @Spy
    FlickrImageSize[] array;

    @Before
    public void setup() {
        filter = mock(IFlickrImageSizeFilter.class);
        mapper = new FlickrImageSizeMapper(filter);

        array = new FlickrImageSize[5];
    }

    @Test
    public void testGetFullImageURLWithNullArray() {
        String fullImageUrl = mapper.getFullImageUrl("big", "really big", null);
        assertTrue(fullImageUrl == null);
    }

    @Test
    public void testGetFullImageUrl() {
        FlickrImageSize size = mock(FlickrImageSize.class);

        String url = "http://fullimage.url";
        array[0] = size;

        when(filter.returnSizeIndex(anyString(), eq(array))).thenReturn(0);
        when(size.getSource()).thenReturn(url);

        assertTrue(mapper.getFullImageUrl("big", "really big", array).equals(url));
    }

    @Test
    public void testFallBackFullImageUrl() {
        FlickrImageSize size = mock(FlickrImageSize.class);

        String url = "http://fullimage.url";
        array[0] = size;

        when(filter.returnSizeIndex(eq("big"), eq(array))).thenReturn(-1);
        when(filter.returnSizeIndex(eq("really big"), eq(array))).thenReturn(0);

        when(size.getSource()).thenReturn(url);

        assertTrue(mapper.getFullImageUrl("big", "really big", array).equals(url));

    }

    @Test
    public void testFallBackURLLookupFailed() {
        FlickrImageSize size = mock(FlickrImageSize.class);
        array[0] = size;

        when(filter.returnSizeIndex(eq("big"), eq(array))).thenReturn(-1);
        when(filter.returnSizeIndex(eq("really big"), eq(array))).thenReturn(-1);

        assertTrue(mapper.getFullImageUrl("big", "really big", array) == null);
    }

    @Test
    public void testgetPreviewUrlWithNullArray() {
        String previewImageUrl = mapper.getFullImageUrl("big", "really big", null);
        assertTrue(previewImageUrl == null);
    }

    @Test
    public void testGetPreviewURL() {
        FlickrImageSize size = mock(FlickrImageSize.class);

        String url = "http://previewimage.url";
        array[0] = size;

        when(filter.returnSizeIndex(anyString(), eq(array))).thenReturn(0);
        when(size.getSource()).thenReturn(url);

        assertTrue(mapper.getPreviewImageUrl("thumbnail", array).equals(url));
    }

    @Test
    public void testGetPreviewURLLookupFailed() {
        FlickrImageSize size = mock(FlickrImageSize.class);
        array[0] = size;

        when(filter.returnSizeIndex(anyString(), eq(array))).thenReturn(-1);

        assertTrue(mapper.getPreviewImageUrl("thumbnail", array) == null);
    }





}
