package net.weissenburger.producebrowser.imageviewer.flickr;

import com.android.volley.toolbox.ImageLoader;

import net.weissenburger.producebrowser.imageviewer.flickr.dataobjects.FlickrImageItem;
import net.weissenburger.producebrowser.imageviewer.flickr.dataobjects.FlickrSearchResponse;
import net.weissenburger.producebrowser.imageviewer.loader.IImageLoaderWrapper;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataAPI;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.loader.ImageLoaderWrapper;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceQuery;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jon Weissenburger on 3/11/18.
 */

public class FlickrDataCoordinator implements IProduceDataCoordinator {

    IProduceDataAPI searchDataAPI = new FlickrSearchRequest();
    IProduceDataAPI imageSizeAPI = new FlickrImageSizesRequest();

    IProduceDataLoader loader;

    IProduceList list;
    IProduceQuery lastQuery;

    int produceLoaded;
    boolean isLoading;

    private IImageLoaderWrapper imageLoader;

    public FlickrDataCoordinator(IProduceDataLoader dataLoader) {
        this.loader = dataLoader;
        imageLoader = new ImageLoaderWrapper();
    }


    @Override
    public IImageLoaderWrapper getImageLoader() {
        return imageLoader;
    }

    @Override
    public void getProduceImages(final IProduceResponseCallback callback, IProduceQuery query) {

        //TODO: make this a comparable
        if (lastQuery != null && lastQuery.getQuery().equals(query.getQuery()) &&
                lastQuery.getPage() == query.getPage()) {

//            if (!isLoading) {
//                callback.onResponse(list.getProduce().toArray(
//                        new IProduce[list.getProduce().size()]));
//            }

            callback.onResponse(list.getProduce().toArray(
                    new IProduce[list.getProduce().size()]));

            return;
        }

        if (!query.isNextPageQuery()) {
            FlickrDataCoordinator.this.list = new FlickrSearchResponse();
        }


        lastQuery = query;
        produceLoaded = 0;
        isLoading = true;

        if (query == null || query.getQuery() == null || query.getQuery().isEmpty()) {
            callback.onError(IProduceResponseCallback.ErrorCode.BAD_QUERY, "Request was empty");
            return;
        }

        // make request for search images
        loader.request(new IProduceResponseCallback() {
            @Override
            public void onResponse(IProduce... produce) {

                if (produce == null || produce.length == 0) {
                    callback.onError(ErrorCode.NO_RESULTS, "No Results");
                    return;
                }

                list.getProduce().addAll(Arrays.asList(produce));

                for (final IProduce produceItem: list.getProduce()) {

                    IProduceQuery query = new ProduceQuery(produceItem.getImageId());

                    loader.request(new IProduceResponseCallback() {
                        @Override
                        public void onResponse(IProduce... produce) {
                            IProduce item = produce[0];

                            produceItem.setFullImageUrl(item.getFullImageUrl());
                            produceItem.setPreviewImageUrl(item.getPreviewImageUrl());

                            checkLoadComplete(callback);
                        }

                        @Override
                        public void onError(ErrorCode e, String message) {
                            // image sizes load failed, just skip this one
                           checkLoadComplete(callback);

                        }
                    }, imageSizeAPI, query, new FlickrImageItemResponseHandler(new FlickrImageItem()));

                }

            }

            @Override
            public void onError(ErrorCode e, String message) {
                callback.onError(e, message);

            }
        }, searchDataAPI, query, new FlickrSearchResponseHandler(new FlickrSearchResponse()));

    }

    private void checkLoadComplete(IProduceResponseCallback callback) {
        if (++produceLoaded >= list.getProduce().size()) {
            callback.onResponse(FlickrDataCoordinator.this.list.getProduce().toArray(
                    new IProduce[FlickrDataCoordinator.this.list.getProduce().size()]));
            isLoading = false;
        }
    }
}
