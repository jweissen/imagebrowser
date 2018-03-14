package net.weissenburger.producebrowser.imageviewer.loader;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class ProduceQuery implements IProduceQuery {

    String query;

    public ProduceQuery(String query) {
        this.query = query;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;

    }
}
