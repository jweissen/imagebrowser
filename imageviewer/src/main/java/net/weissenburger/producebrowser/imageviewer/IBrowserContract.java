package net.weissenburger.producebrowser.imageviewer;

import net.weissenburger.producebrowser.base.IPresenter;
import net.weissenburger.producebrowser.base.IView;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IBrowserContract {

    interface IBrowserView extends IView {

        void showLoadingView();
        void showProduceImages(IProduce... produceList);
        void navigateToFullView();
        void showNoResultsFound();
        void showGeneralError();
        void repromptQuery();
    }

    interface IBrowserPresenter extends IPresenter {
        void getProduce(IProduceQuery query);
        void onProduceClicked();
    }

}
