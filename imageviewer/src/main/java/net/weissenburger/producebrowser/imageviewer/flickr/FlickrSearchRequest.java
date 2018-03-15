package net.weissenburger.producebrowser.imageviewer.flickr;

import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataAPI;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class FlickrSearchRequest implements IProduceDataAPI {

    static final String API_KEY = "0af8a510771f79c054c0589a310f9887";

    @Override
    public String getURL(IProduceQuery query) throws UnsupportedEncodingException {
        String encodedURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                "&api_key=" + API_KEY +
                "&text=" + URLEncoder.encode(query.getQuery(), "UTF-8") + "&format=json&nojsoncallback=1";

        return encodedURL;
    }

    //https://api.flickr.com/services/rest/?method=flickr.photos.search


}
