package net.weissenburger.producebrowser.imageviewer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import net.weissenburger.producebrowser.base.IPresenterHolder;
import net.weissenburger.producebrowser.base.PresenterBinding;
import net.weissenburger.producebrowser.base.PresenterHolder;
import net.weissenburger.producebrowser.base.networking.JsonRequestWrapper;
import net.weissenburger.producebrowser.base.networking.VolleyNetworkManager;
import net.weissenburger.producebrowser.base.networking.VolleyRequest;
import net.weissenburger.producebrowser.imageviewer.flickr.FlickrDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceDataCoordinator;
import net.weissenburger.producebrowser.imageviewer.loader.IProduceQuery;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceDataLoader;
import net.weissenburger.producebrowser.imageviewer.loader.ProduceQuery;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;


public class ImageBrowserActivity extends AppCompatActivity implements IBrowserContract.IBrowserView {

    IBrowserContract.IBrowserPresenter presenter;
    RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    ProgressBar progressBar;

    boolean endlessScrollLoading = false;
    IProduceQuery currentQuery;
    ImageAdapter adapter;

    private static final String ENDLESS_SCROLL_KEY = "endless_scroll_key";
    private static final String CURRENT_QUERY_KEY = "current_query_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);

        recyclerView = findViewById(R.id.image_browser_recycler_view);
        layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.columns));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if ( !endlessScrollLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount-10)
                        {
                            endlessScrollLoading = true;
                            currentQuery = currentQuery.nextPage();

                            presenter.getProduce(currentQuery);

                        }
                    }
                }
        });

        // default to 'tomatoes' for now
        currentQuery = new ProduceQuery("tomatoes");

        progressBar = findViewById(R.id.image_browser_progress_bar);

        if (savedInstanceState == null) {
            presenter = new ImageBrowserPresenter(getProduceDataCoordinator());
        } else {
            presenter = (ImageBrowserPresenter) PresenterBinding.getPresenter(this);
            currentQuery = savedInstanceState.getParcelable(CURRENT_QUERY_KEY);
            endlessScrollLoading = savedInstanceState.getBoolean(ENDLESS_SCROLL_KEY);
        }

        PresenterBinding.bindPresenter(presenter, this);


        presenter.getProduce(currentQuery);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ENDLESS_SCROLL_KEY, endlessScrollLoading);
        outState.putParcelable(CURRENT_QUERY_KEY, currentQuery);
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
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProduceImages(IProduce... list) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        if (adapter == null) {
            adapter = new ImageAdapter(list, presenter.getImageLoader().unwrap(this));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateDataSet(list);
            adapter.notifyItemInserted(currentQuery.getPage() * 30);
        }


        endlessScrollLoading = false;

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
