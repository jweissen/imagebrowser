package net.weissenburger.producebrowser.imageviewer.loader;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;
import net.weissenburger.producebrowser.imageviewer.model.IProduceList;

/**
 * Created by Jon Weissenburger on 3/6/18.
 */

public interface IProduceResponseCallback {

    enum ErrorCode {
        UNKNOWN,
        BAD_QUERY,
        NO_RESULTS,
        SERVICE_UNAVAILABLE,
        BAD_API_REQUEST
    }


    void onResponse(IProduce... produce);
    void onError(ErrorCode e, String message);
}
