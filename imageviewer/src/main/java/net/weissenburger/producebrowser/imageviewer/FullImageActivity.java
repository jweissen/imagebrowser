package net.weissenburger.producebrowser.imageviewer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import net.weissenburger.producebrowser.base.PresenterBinding;
import net.weissenburger.producebrowser.imageviewer.model.IProduce;

/**
 * Created by Jon Weissenburger on 3/17/18.
 */

public class FullImageActivity extends AppCompatActivity implements IFullImageContract.IFullImageView{

    public static final String PRODUCE_ITEM = "produce_item";

    NetworkImageView imageView;
    TextView captionTextView;
    IFullImageContract.IFullImagePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        getSupportActionBar().hide();

        imageView = findViewById(R.id.full_image_iv);
        captionTextView = findViewById(R.id.full_image_caption);



        if (savedInstanceState == null) {
            presenter = new FullImagePresenter();
        } else {
            presenter = (IFullImageContract.IFullImagePresenter) PresenterBinding.getPresenter(this);
        }

        PresenterBinding.bindPresenter(presenter, this);

        IProduce item = getIntent().getParcelableExtra(PRODUCE_ITEM);


        if (item != null) {
//            imageView.setLayoutParams(new RelativeLayout.LayoutParams(item.getFullImageWidth(),
//                    item.getFullImageHeight()));

            presenter.loadImage(imageView, item);
            captionTextView.setText(item.getCaption());
        } else {
            // no image item passed, nothing to display...
            finish();
        }
    }


    @Override
    public FragmentActivity getActivityRef() {
        return this;
    }
}
