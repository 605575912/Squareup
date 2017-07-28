package com.squareup.code;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.lib.ImageUtils;

import java.util.ArrayList;

/**
 * Created by liangzhenxiong on 2017/04/07 0007.
 * imay红人三列图片
 */

public class GridImageLayout extends ViewGroup implements View.OnClickListener {
    ImageView imageView0, imageView1, imageView2;
    ArrayList<String> feeds;
    String title;
    boolean istopic;

    public GridImageLayout(Context context) {
        super(context);
        init(context);
    }

    public GridImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridImageLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        setBackgroundColor(Color.WHITE);
        imageView0 = new ImageView(context);
        imageView0.setId(R.id.iv_dynimg0_id);
//        imageView0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView1 = new ImageView(context);
        imageView1.setId(R.id.iv_dynimg1_id);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2 = new ImageView(context);
        imageView2.setId(R.id.iv_dynimg2_id);
//        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(imageView0);
        imageView1.setBackgroundColor(Color.GREEN);
        addView(imageView1);
        addView(imageView2);
        imageView0.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    public void setModels(ArrayList<String> feeds, String title) {
        setModels(feeds, title, false);
    }

    public void setModels(ArrayList<String> feeds, String title, boolean istopic) {
        this.title = title;
        this.istopic = istopic;
        imageView0.setTag(R.id.searchfeed_id, null);
        imageView1.setTag(R.id.searchfeed_id, null);
        imageView2.setTag(R.id.searchfeed_id, null);
        imageView1.setImageResource(0);
        imageView2.setImageResource(0);
        if (feeds != null && feeds.size() > 0) {
            this.feeds = feeds;
            for (int i = 0; i < feeds.size(); i++) {
                if (setImageView(imageView0, feeds.get(i), i)) {

                } else if (setImageView(imageView1, feeds.get(i), i)) {

                } else if (setImageView(imageView2, feeds.get(i), i)) {

                } else {
                    break;
                }
            }
        } else {
            imageView0.setImageResource(R.drawable.placeholder);
        }

    }

    private boolean setImageView(ImageView imageView, String feedModel, int postion) {
        if (imageView.getTag(R.id.searchfeed_id) == null) {

            imageView.setTag(R.id.searchfeed_id, postion);
            loadImg(imageView, feedModel);
            return true;
        }
        return false;
    }

    void loadImg(ImageView imageView, String img) {
        if (!TextUtils.isEmpty(img)) {
            imageView.setVisibility(VISIBLE);
            ImageUtils.loadRoundImage(getContext(), img, imageView, R.drawable.placeholder);
        } else {
            imageView.setVisibility(INVISIBLE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float pading = getResources().getDimension(R.dimen.imayhot_padding);
        int left = (int) pading;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(left, 0, left + view.getMeasuredWidth(), view.getMeasuredHeight());
            left = (int) (left + view.getMeasuredWidth() + pading);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        float pading = (getChildCount() + 1) * getResources().getDimension(R.dimen.imayhot_padding);
        float childw = (sizeWidth - pading) / getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec((int) childw, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int) childw, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(sizeWidth, (int) childw);
    }

    @Override
    public void onClick(View v) {
        if (feeds == null) {
            return;
        }
        Object object = v.getTag(R.id.searchfeed_id);
        if (object != null && object instanceof Integer) {
            int postion = (int) object;


        }

    }
}
