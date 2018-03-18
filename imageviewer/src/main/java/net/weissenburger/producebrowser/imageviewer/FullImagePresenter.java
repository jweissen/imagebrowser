package net.weissenburger.producebrowser.imageviewer;

import com.android.volley.toolbox.NetworkImageView;

import net.weissenburger.producebrowser.base.IView;
import net.weissenburger.producebrowser.imageviewer.loader.IImageLoaderWrapper;
import net.weissenburger.producebrowser.imageviewer.loader.ImageLoaderWrapper;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;

/**
 * Created by Jon Weissenburger on 3/17/18.
 */

public class FullImagePresenter implements IFullImageContract.IFullImagePresenter {

    IFullImageContract.IFullImageView view;
    IImageLoaderWrapper imageLoaderWrapper;

    public FullImagePresenter() {
        imageLoaderWrapper = new ImageLoaderWrapper();
    }

    @Override
    public void attachView(IView view) {
        this.view = (IFullImageContract.IFullImageView) view;
    }

    @Override
    public void loadImage(NetworkImageView imageView, IProduce item) {
        imageView.setImageUrl(item.getFullImageUrl(), imageLoaderWrapper.unwrap(view.getActivityRef().getApplicationContext()));

    }
}
