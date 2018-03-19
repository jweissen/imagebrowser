package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataAPI;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/18/18.
 */

public class FlickrImageSizesRequestTest {

    IProduceDataAPI dataAPI;

    @Before
    public void setup() {
        dataAPI = new FlickrImageSizesRequest();
    }

    @Test
    public void testBasicQuery() {
        String searchKey = "1234567";

        IProduceQuery query = mock(IProduceQuery.class);
        when(query.getQuery()).thenReturn(searchKey);


        try {
            assertTrue(dataAPI.getURL(query).contains(searchKey));

        } catch (UnsupportedEncodingException e) {

            assertTrue(false);
        }
    }

    @Test
    public void testEncodedQuery() {
        String searchKey = "123$&/+AeK";

        IProduceQuery query = mock(IProduceQuery.class);
        when(query.getQuery()).thenReturn(searchKey);

        try {
            dataAPI.getURL(query);

            assertTrue(URLDecoder.decode(dataAPI.getURL(query), "UTF-8").contains(searchKey));

        } catch(UnsupportedEncodingException e) {

            assertTrue(false);
        }
    }




}
