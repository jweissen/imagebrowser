package net.weissenburger.producebrowser.imageviewer;

import net.weissenburger.producebrowser.base.IView;
import net.weissenburger.producebrowser.imageviewer.loader.IImageLoaderWrapper;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import java.util.ArrayList;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public class ImageBrowserPresenter implements IBrowserContract.IBrowserPresenter {

    IBrowserContract.IBrowserView view;
    IProduceDataCoordinator dataCoordinator;


    public ImageBrowserPresenter(IProduceDataCoordinator dataCoordinator) {
        this.dataCoordinator = dataCoordinator;
    }

    @Override
    public void getProduce(IProduceQuery query) {
        if (!query.isNextPageQuery()) {
            // fresh search, show loading view
            view.showLoadingView();
        }

        dataCoordinator.getProduceImages(new IProduceResponseCallback() {
            @Override
            public void onResponse(IProduce... list) {
                view.showProduceImages(list);
            }

            @Override
            public void onError(ErrorCode e, String message) {
                switch (e) {
                    case UNKNOWN:
                    case SERVICE_UNAVAILABLE:
                        view.showGeneralError();
                        break;
                    case BAD_QUERY:
                        view.repromptQuery();
                        break;
                    case NO_RESULTS:
                        view.showNoResultsFound();
                }
            }
        }, query);

    }

    @Override
    public void onProduceClicked() {

    }

    @Override
    public IImageLoaderWrapper getImageLoader() {
        return dataCoordinator.getImageLoader();
    }

    @Override
    public void attachView(IView view) {
        this.view = (IBrowserContract.IBrowserView) view;
    }
}
