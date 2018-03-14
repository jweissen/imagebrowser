package net.weissenburger.producebrowser.imageviewer.flickr;

import junit.framework.Assert;

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
 * Created by Jon Weissenburger on 3/7/18.
 */

public class FlickrSearchRequestTest {

    IProduceDataAPI dataAPI;

    @Before
    public void setup() {
        dataAPI = new FlickrSearchRequest();
    }

    @Test
    public void testBasicQuery() {
        String searchText = "tomatoes";

        IProduceQuery query = mock(IProduceQuery.class);
        when(query.getQuery()).thenReturn(searchText);


        try {
            assertTrue(dataAPI.getURL(query).contains(searchText));

        } catch (UnsupportedEncodingException e) {

            assertTrue(false);
        }
    }

    @Test
    public void testEncodedQuery() {
        String searchText = "ripe tomatoes";

        IProduceQuery query = mock(IProduceQuery.class);
        when(query.getQuery()).thenReturn(searchText);

        try {
            dataAPI.getURL(query);

            assertTrue(URLDecoder.decode(dataAPI.getURL(query), "UTF-8").contains(searchText));

        } catch(UnsupportedEncodingException e) {

            assertTrue(false);
        }
    }

}
