package net.weissenburger.producebrowser.imageviewer;

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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    IProduce[] list;
    ImageLoader imageLoader;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView image;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image_item_imageview);
        }
    }

    public ImageAdapter(IProduce[] list, ImageLoader imageLoader) {
        this.list = list;
        this.imageLoader = imageLoader;
    }


    public void updateDataSet(IProduce[] list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageUrl(list[position].getPreviewImageUrl(), imageLoader);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

}
