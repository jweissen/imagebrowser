package net.weissenburger.producebrowser.base;

/**
 * Created by Jon Weissenburger on 1/27/17.
 */

public interface IPresenterBinding {

    void bindPresenter(IPresenter presenter, IView view);
    IPresenter getPresenter(IView view);
}
