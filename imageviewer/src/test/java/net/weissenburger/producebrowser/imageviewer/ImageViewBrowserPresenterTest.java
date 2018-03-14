package net.weissenburger.producebrowser.imageviewer;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public class ImageViewBrowserPresenterTest {

    IBrowserContract.IBrowserView view;
    IBrowserContract.IBrowserPresenter presenter;
    IProduceDataCoordinator dataCoordinator;

    @Before
    public void setup() {
        dataCoordinator = mock(IProduceDataCoordinator.class);
        view = mock(IBrowserContract.IBrowserView.class);

        presenter = new ImageBrowserPresenter(dataCoordinator);
        presenter.attachView(view);
    }

    @Test
    public void testLoadingViewPresentedOnQuery() {
        presenter.getProduce(mock(IProduceQuery.class));
        verify(view).showLoadingView();
    }

    @Test
    public void testDataLoadWithResponseList() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback callback = (IProduceResponseCallback) invocation.getArguments()[0];
                callback.onResponse(mock(IProduce.class));

                return null;
            }
        }).when(dataCoordinator).getProduceImages(any(IProduceResponseCallback.class), any(IProduceQuery.class));

        presenter.getProduce(mock(IProduceQuery.class));

        verify(view).showProduceImages(any(IProduce.class));
    }

    @Test
    public void testDataLoadWithNoResults() {

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback callback = (IProduceResponseCallback) invocation.getArguments()[0];
                callback.onError(IProduceResponseCallback.ErrorCode.NO_RESULTS, "No Results Found");

                return null;
            }
        }).when(dataCoordinator).getProduceImages(any(IProduceResponseCallback.class), any(IProduceQuery.class));

        presenter.getProduce(mock(IProduceQuery.class));

        verify(view).showNoResultsFound();
    }

    @Test
    public void testServiceUnreachable() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback callback = (IProduceResponseCallback) invocation.getArguments()[0];
                callback.onError(IProduceResponseCallback.ErrorCode.SERVICE_UNAVAILABLE, "Service is currently unavailable");
                return null;
            }
        }).when(dataCoordinator).getProduceImages(any(IProduceResponseCallback.class), any(IProduceQuery.class));

        presenter.getProduce(mock(IProduceQuery.class));

        verify(view).showGeneralError();
    }


    @Test
    public void testUnknownError() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback callback = (IProduceResponseCallback) invocation.getArguments()[0];
                callback.onError(IProduceResponseCallback.ErrorCode.UNKNOWN, "An unknown error has occurred");
                return null;
            }
        }).when(dataCoordinator).getProduceImages(any(IProduceResponseCallback.class), any(IProduceQuery.class));

        presenter.getProduce(mock(IProduceQuery.class));

        verify(view).showGeneralError();
    }

    @Test
    public void testInvalidQuery() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                IProduceResponseCallback callback = (IProduceResponseCallback) invocation.getArguments()[0];
                callback.onError(IProduceResponseCallback.ErrorCode.BAD_QUERY, "Query is invalid");
                return null;
            }
        }).when(dataCoordinator).getProduceImages(any(IProduceResponseCallback.class), any(IProduceQuery.class));

        presenter.getProduce(mock(IProduceQuery.class));

        verify(view).repromptQuery();
    }

}
