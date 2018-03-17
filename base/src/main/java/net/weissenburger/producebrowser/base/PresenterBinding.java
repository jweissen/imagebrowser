package net.weissenburger.producebrowser.base;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Jon Weissenburger on 1/27/17.
 */

public class PresenterBinding {

    IPresenterHolder holder;

    final static String PRESENTER_HOLDER_TAG = "PRESENTER_HOLDER";


    public static void bindPresenter(IPresenter presenter, IView view) {
        IPresenterHolder holder = getHolder(view);

        if (!(holder instanceof Fragment))
            throw new ClassCastException("IPresenterHolder must extend Fragment!");

        presenter.attachView(view);
        holder.setPresenter(presenter);
    }

    private static IPresenterHolder getHolder(IView view) {
        IPresenterHolder holder = (IPresenterHolder) view.getActivityRef().getSupportFragmentManager().
                findFragmentByTag(PRESENTER_HOLDER_TAG);

        if (holder == null) {
            holder = new PresenterHolder();

            view.getActivityRef().getSupportFragmentManager().
                    beginTransaction().
                    add((Fragment)holder, PRESENTER_HOLDER_TAG).commit();
        }

        return holder;
    }

    public static IPresenter getPresenter(IView view) {
        IPresenterHolder holder = getHolder(view);

        if (holder == null) {
            Log.w("PresenterBinding", "Presenter holder doesn't exist on fragment manager!");
            return null;
        }

        return holder.getPresenter();
    }
}
