package net.weissenburger.producebrowser.imageviewer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;

/**
 * Created by Jon Weissenburger on 3/15/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    IProduce[] list;
    ImageLoader imageLoader;
    ImageClickDelegate delegate;

    private static final int VIEW_TYPE_IMAGE = 0;
    private static final int VIEW_TYPE_FOOTER = 1;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NetworkImageView image;
        RowClickDelegate delegate;

        public ViewHolder(View v, RowClickDelegate delegate) {
            super(v);
            image = v.findViewById(R.id.image_item_imageview);
            this.delegate = delegate;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) {
                delegate.onClick(getAdapterPosition());
            }
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public ImageAdapter(IProduce[] list, ImageLoader imageLoader, ImageClickDelegate delegate) {
        this.list = list;
        this.imageLoader = imageLoader;
        this.delegate = delegate;
    }


    public void updateDataSet(IProduce[] list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_footer, parent, false);
            vh = new FooterViewHolder(v);
        } else {
            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            vh = new ViewHolder(v, new RowClickDelegate() {
                @Override
                public void onClick(int position) {
                    if (delegate != null) {
                        delegate.onItemClicked(v.findViewById(R.id.image_item_imageview), list[position]);
                    }
                }
            });
        }

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.length) {
            return VIEW_TYPE_FOOTER;
        }

        return VIEW_TYPE_IMAGE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            NetworkImageView imageView = ((ViewHolder) holder).image;
            imageView.setImageUrl(list[position].getPreviewImageUrl(), imageLoader);
            imageView.setDefaultImageResId(R.drawable.default_image);
            //imageView.setErrorImageResId();
        }
    }

    @Override
    public int getItemCount() {
        return list.length + 1;
    }

    private interface RowClickDelegate {
        void onClick(int position);
    }

    public interface ImageClickDelegate {
        void onItemClicked(View view, IProduce itemSelected);
    }

}
