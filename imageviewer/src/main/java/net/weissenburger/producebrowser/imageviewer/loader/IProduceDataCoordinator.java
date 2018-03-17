package net.weissenburger.producebrowser.imageviewer.loader;

/**
 * Created by Jon Weissenburger on 3/11/18.
 */

public interface IProduceDataCoordinator {

    IImageLoaderWrapper getImageLoader();
    void getProduceImages(IProduceResponseCallback callback, IProduceQuery query);

}
