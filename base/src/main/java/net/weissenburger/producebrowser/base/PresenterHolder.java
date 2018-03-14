package net.weissenburger.producebrowser.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


/**
 * Created by Jon Weissenburger on 1/27/17.
 */

public class PresenterHolder extends Fragment implements IPresenterHolder {

    IPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

}
