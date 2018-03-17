package net.weissenburger.producebrowser.imageviewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ImageDividerItemDecorator extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public ImageDividerItemDecorator(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider, context.getTheme());
    }



    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

            int ntop = child.getTop();
            int nleft = child.getWidth() + child.getLeft() + params.rightMargin;
            int nright = nleft + mDivider.getIntrinsicWidth();
            int nbottom = child.getBottom() + params.bottomMargin;

            mDivider.setBounds(nleft, ntop, nright, nbottom);
            mDivider.draw(c);
        }
    }
}