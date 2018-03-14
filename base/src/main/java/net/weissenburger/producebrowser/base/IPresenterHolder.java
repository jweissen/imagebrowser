package net.weissenburger.producebrowser.base;

/**
 * Created by Jon Weissenburger on 1/27/17.
 */

public interface IPresenterHolder {

    IPresenter getPresenter();
    void setPresenter(IPresenter presenter);
}
