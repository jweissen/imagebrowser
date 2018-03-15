package net.weissenburger.producebrowser.imageviewer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.weissenburger.producebrowser.base.IPresenterBinding;
import net.weissenburger.producebrowser.base.PresenterBinding;
import net.weissenburger.producebrowser.base.PresenterHolder;
import net.weissenburger.producebrowser.base.networking.JsonRequestWrapper;
import net.weissenburger.producebrowser.base.networking.VolleyNetworkManager;
import net.weissenburger.producebrowser.base.networking.VolleyRequest;
import net.weissenburger.producebrowser.imageviewer.flickr.FlickrDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.flickr.FlickrSearchResponse;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceQuery;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;


public class ImageBrowserActivity extends AppCompatActivity implements IBrowserContract.IBrowserView {

    IBrowserContract.IBrowserPresenter presenter;
    IPresenterBinding presenterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);

        if (savedInstanceState == null) {
            presenterBinding = new PresenterBinding(new PresenterHolder());
            presenter = new ImageBrowserPresenter(getProduceDataCoordinator());
            presenterBinding.bindPresenter(presenter, this);
        } else {
            presenter = (ImageBrowserPresenter) presenterBinding.getPresenter(this);
        }

        presenter.getProduce(new ProduceQuery("tomatoes"));
    }

    // Creating dependency tree
    private IProduceDataCoordinator getProduceDataCoordinator() {

        // set up request for FlickrAPIs
        return new FlickrDataCoordinator(new ProduceDataLoader(this, new VolleyNetworkManager.VolleyNetworkManagerBuilder(),
                new VolleyRequest.VolleyRequestBuilder(), new JsonRequestWrapper()));

        //TODO: allow multiple sources of data?

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showProduceImages(IProduce... list) {
        Log.i("ImageBrowserActivity", "list = {}" + list);

    }

    @Override
    public void navigateToFullView() {

    }

    @Override
    public void showNoResultsFound() {

    }

    @Override
    public void showGeneralError() {

    }

    @Override
    public void repromptQuery() {

    }

    @Override
    public FragmentActivity getActivityRef() {
        return this;
    }
}
