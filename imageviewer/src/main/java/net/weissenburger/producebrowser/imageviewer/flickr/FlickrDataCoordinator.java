package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataAPI;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceResponseCallback;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceQuery;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

import java.util.Arrays;

/**
 * Created by Jon Weissenburger on 3/11/18.
 */

public class FlickrDataCoordinator implements IProduceDataCoordinator {

    IProduceDataAPI searchDataAPI = new FlickrSearchRequest();
    IProduceDataAPI imageSizeAPI = new FlickrImageSizesRequest();

    IProduceDataLoader loader;

    IProduceList list;

    int produceLoaded;

    public FlickrDataCoordinator(IProduceDataLoader dataLoader) {
        this.loader = dataLoader;
    }


    @Override
    public void getProduceImages(final IProduceResponseCallback callback, IProduceQuery query) {

        produceLoaded = 0;

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

                FlickrDataCoordinator.this.list = new FlickrSearchResponse(Arrays.asList(produce));

                for (final IProduce produceItem: list.getProduce()) {

                    IProduceQuery query = new ProduceQuery(produceItem.getImageId());

                    loader.request(new IProduceResponseCallback() {
                        @Override
                        public void onResponse(IProduce... produce) {
                            IProduce item = produce[0];

                            produceItem.setCaption(item.getCaption());
                            produceItem.setFullImageUrl(item.getFullImageUrl());
                            produceItem.setPreviewImageUrl(item.getPreviewImageUrl());

                            if (++produceLoaded >= list.getProduce().size()) {
                                callback.onResponse(FlickrDataCoordinator.this.list.getProduce().toArray(
                                        new IProduce[FlickrDataCoordinator.this.list.getProduce().size()]));
                            }

                        }

                        @Override
                        public void onError(ErrorCode e, String message) {
                            callback.onError(e, message);

                        }
                    }, imageSizeAPI, query);

                }

            }

            @Override
            public void onError(ErrorCode e, String message) {
                callback.onError(e, message);

            }
        }, searchDataAPI, query);




    }
}
