package net.weissenburger.producebrowser.imageviewer.loader;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jon Weissenburger on 3/7/18.
 */

public class ProduceQuery implements IProduceQuery {

    String query;
    int page;
    boolean nextPageQuery;

    public ProduceQuery(String query) {
        this.query = query;
        page = 1;   // default to page 1
        nextPageQuery = false;
    }

    // private constructor for next page request
    private ProduceQuery(String query, int page) {
        this.query = query;
        this.page = page;
        nextPageQuery = true;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public IProduceQuery nextPage() {
        return new ProduceQuery(query, page+1);
    }


    @Override
    public boolean isNextPageQuery() {
        return nextPageQuery;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(query);
        parcel.writeInt(page);
        parcel.writeByte(isNextPageQuery() ? (byte) 0 : 1);
    }


    public static final ProduceQuery.Creator<ProduceQuery> CREATOR
            = new ProduceQuery.Creator<ProduceQuery>() {
        public ProduceQuery createFromParcel(Parcel in) {
            return new ProduceQuery(in);
        }

        public ProduceQuery[] newArray(int size) {
            return new ProduceQuery[size];
        }
    };

    private ProduceQuery(Parcel in) {
        query = in.readString();
        page = in.readInt();
        nextPageQuery = in.readByte() == 0;
    }
}
