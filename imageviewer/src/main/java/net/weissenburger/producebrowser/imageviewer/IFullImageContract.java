package net.weissenburger.producebrowser.imageviewer;

import com.android.volley.toolbox.NetworkImageView;

import net.weissenburger.producebrowser.base.IPresenter;
import net.weissenburger.producebrowser.base.IView;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;

/**
 * Created by Jon Weissenburger on 3/17/18.
 */

public interface IFullImageContract {

    interface IFullImageView extends IView {

    }

    interface IFullImagePresenter extends IPresenter {
        void loadImage(NetworkImageView imageView, IProduce item);
    }

}
