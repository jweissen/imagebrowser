package net.weissenburger.producebrowser.imageviewer.loader;

import android.os.Parcelable;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduceQuery extends Parcelable {

    String getQuery();
    void setQuery(String query);
    IProduceQuery nextPage();
    boolean isNextPageQuery();
    int getPage();

}
