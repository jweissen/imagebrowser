package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.flickr.dataobjects.FlickrImageItem;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataAPI;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseHandler;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/14/18.
 */

public class FlickrDataCoordinatorTest {

    IProduceDataCoordinator dataCoordinator;
    IProduceDataLoader dataLoader;

    @Before
    public void setup() {
        dataLoader = mock(IProduceDataLoader.class);

        dataCoordinator = new FlickrDataCoordinator(dataLoader);
    }

    @Test
    public void testNullQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);

        dataCoordinator.getProduceImages(callback, null);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }

    @Test
    public void testNullQueryString() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn(null);

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }

    @Test
    public void testEmptyQueryString() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("");

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.BAD_QUERY), anyString());
    }


    @Test
    public void testNullReturnedFromSearchQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("tomatoes");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(null);
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(IProduceDataAPI.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.NO_RESULTS), anyString());
    }

    @Test
    public void testEmptySetReturnedFromSearchQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("tomatoes");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(new IProduce[0]);
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(IProduceDataAPI.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.NO_RESULTS), anyString());
    }


    @Test
    public void testResultsFromSearchQueryReturned() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceDataAPI dataAPI = mock(IProduceDataAPI.class);
        IProduceQuery query = mock(IProduceQuery.class);


        final IProduce responseObj = new FlickrImageItem();
        responseObj.setImageId("123456");

        final String fullImageUrl = "http://fullimage.url";
        final String previewImageUrl = "http://previewimage.url";
        final int fullImageHeight = 800;
        final int fullImageWidth = 600;

        final IProduce imageResponseObj = mock(IProduce.class);

        when(imageResponseObj.getFullImageUrl()).thenReturn(fullImageUrl);
        when(imageResponseObj.getPreviewImageUrl()).thenReturn(previewImageUrl);
        when(imageResponseObj.getFullImageHeight()).thenReturn(fullImageHeight);
        when(imageResponseObj.getFullImageWidth()).thenReturn(fullImageWidth);

        when(query.getQuery()).thenReturn("tomatoes");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(responseObj);
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(imageResponseObj);

                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrImageSizesRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduce produce = (IProduce) invocation.getArguments()[0];

                // validate values are being set correctly on outer list object
                assertEquals(produce.getFullImageUrl(), fullImageUrl);
                assertEquals(produce.getPreviewImageUrl(), previewImageUrl);
                assertEquals(produce.getFullImageHeight(), fullImageHeight);
                assertEquals(produce.getFullImageWidth(), fullImageWidth);

                return null;
            }

        }).when(callback).onResponse(Mockito.<IProduce>any());


        dataCoordinator.getProduceImages(callback, query);

        // verify final onResponse was called (all code executed)
        verify(callback).onResponse(Mockito.<IProduce>any());

    }


    @Test
    public void testErrorCallbackFromSearchRequest() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("tomatoes");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onError(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE, "Service Down");
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onError(eq(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE), anyString());
    }


    @Test
    public void testErrorCallbackFromImageSizeRequest() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("tomatoes");

        final IProduce responseObj = new FlickrImageItem();
        responseObj.setImageId("123456");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(responseObj);
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onError(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE, "Service Down");

                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrImageSizesRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onResponse(Mockito.<IProduce>any());
    }

    @Test
    public void testNextPageQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        String queryString = "tomato";
        int page = 1;

        when(query.getQuery()).thenReturn(queryString);
        when(query.getPage()).thenReturn(page);

        // verify data loader is only invoked on first call
        dataCoordinator.getProduceImages(callback, query);

        page = 2;
        when(query.getPage()).thenReturn(page);

        dataCoordinator.getProduceImages(callback, query);

        verify(dataLoader, atMost(1)).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));
    }

    @Test
    public void testMultipleResponsesFromSearch() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        when(query.getQuery()).thenReturn("tomatoes");

        final IProduce one = new FlickrImageItem();
        one.setImageId("123456");

        final IProduce two = new FlickrImageItem();
        two.setImageId("654321");


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                cb.onResponse(one, two);    // return 2 results
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                    IProduceResponseCallback cb = (IProduceResponseCallback) invocation.getArguments()[0];
                    cb.onResponse(mock(IProduce.class));
                return null;
            }
        }).when(dataLoader).request(any(IProduceResponseCallback.class), any(FlickrImageSizesRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] produces = invocation.getArguments();
                assertTrue(produces.length == 2);
                return null;
            }
        }).when(callback).onResponse(Mockito.<IProduce>any());

        dataCoordinator.getProduceImages(callback, query);

        verify(callback).onResponse(Mockito.<IProduce>any());

    }

    @Test
    public void testDuplicateQuery() {
        IProduceResponseCallback callback = mock(IProduceResponseCallback.class);
        IProduceQuery query = mock(IProduceQuery.class);

        String queryString = "tomato";
        int page = 1;

        when(query.getQuery()).thenReturn(queryString);
        when(query.getPage()).thenReturn(page);

        // verify data loader is only invoked on first call
        dataCoordinator.getProduceImages(callback, query);
        dataCoordinator.getProduceImages(callback, query);
        verify(dataLoader, atMost(1)).request(any(IProduceResponseCallback.class), any(FlickrSearchRequest.class), any(IProduceQuery.class), any(IProduceResponseHandler.class));
    }

    @Test
    public void testImageLoaderWrapperInitialized() {
        assertNotNull(dataCoordinator.getImageLoader());
    }



}
