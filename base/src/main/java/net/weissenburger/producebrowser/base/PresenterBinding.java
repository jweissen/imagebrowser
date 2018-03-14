package net.weissenburger.producebrowser.base;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Jon Weissenburger on 1/27/17.
 */

public class PresenterBinding implements IPresenterBinding {

    IPresenterHolder holder;

    final String PRESENTER_HOLDER_TAG = "PRESENTER_HOLDER";


    public PresenterBinding(IPresenterHolder holder) {
        this.holder = holder;
    }

    @Override
    public void bindPresenter(IPresenter presenter, IView view) {
        if (!(holder instanceof Fragment))
            throw new ClassCastException("IPresenterHolder must extend Fragment!");

        presenter.attachView(view);
        holder.setPresenter(presenter);

        view.getActivityRef().getSupportFragmentManager().
                beginTransaction().
                add((Fragment)holder, PRESENTER_HOLDER_TAG).commit();
    }

    @Override
    public IPresenter getPresenter(IView view) {
        holder = (IPresenterHolder) view.getActivityRef().getSupportFragmentManager().
                findFragmentByTag(PRESENTER_HOLDER_TAG);

        if (holder == null) {
            Log.w("PresenterBinding", "Presenter holder doesn't exist on fragment manager!");
            return null;
        }

        return holder.getPresenter();
    }
}
