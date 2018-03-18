package net.weissenburger.producebrowser.imageviewer;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import java.io.ByteArrayOutputStream;


public class ImageBrowserActivity extends AppCompatActivity implements IBrowserContract.IBrowserView, ImageAdapter.ImageClickDelegate {

    IBrowserContract.IBrowserPresenter presenter;
    RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    ProgressBar progressBar;
    Toolbar toolbar;

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

        // have loading spinner span entire row
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount()-1) {
                    return getResources().getInteger(R.integer.columns);
                } else {
                    return 1;
                }
            }
        });

        recyclerView.addItemDecoration(new ImageDividerItemDecorator(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if ( !endlessScrollLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount-4)
                        {
                            endlessScrollLoading = true;
                            currentQuery = currentQuery.nextPage();

                            presenter.getProduce(currentQuery);

                        }
                    }
                }
        });

        String initialQuery = getIntent().getData().getLastPathSegment();
        currentQuery = new ProduceQuery(initialQuery);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(initialQuery);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.image_browser_progress_bar);

        if (savedInstanceState == null) {
            presenter = new ImageBrowserPresenter(getProduceDataCoordinator());
        } else {
            presenter = (ImageBrowserPresenter) PresenterBinding.getPresenter(this);
            currentQuery = savedInstanceState.getParcelable(CURRENT_QUERY_KEY);
            endlessScrollLoading = savedInstanceState.getBoolean(ENDLESS_SCROLL_KEY);

            if (currentQuery != null) {
                toolbar.setTitle(currentQuery.getQuery());
            }
        }

        PresenterBinding.bindPresenter(presenter, this);


        presenter.getProduce(currentQuery);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.produce_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                currentQuery = new ProduceQuery(query);
                presenter.getProduce(currentQuery);
                toolbar.setTitle(query);
                adapter = null;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
            adapter = new ImageAdapter(list, presenter.getImageLoader().unwrap(this), this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateDataSet(list);
            adapter.notifyItemInserted(currentQuery.getPage() * 30);
        }


        endlessScrollLoading = false;

    }

    @Override
    public void navigateToFullView(View v, IProduce produce) {
        Intent i = new Intent(this, FullImageActivity.class);
        i.putExtra(FullImageActivity.PRODUCE_ITEM, produce);

        String transitionName = getString(R.string.full_image_transition);

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, v, transitionName);
        // start the new activity
        startActivity(i, options.toBundle());

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


    @Override
    public void onItemClicked(View view, IProduce itemSelected) {
        if (itemSelected != null) {
            navigateToFullView(view, itemSelected);
        }
    }
}
