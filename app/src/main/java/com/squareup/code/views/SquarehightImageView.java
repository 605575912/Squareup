package com.squareup.code.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by liangzhenxiong on 2017/04/08 0008.
 */

public class SquarehightImageView extends ImageView {
    public SquarehightImageView(Context context) {
        super(context);
        init(context);
    }

    public SquarehightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SquarehightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquarehightImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(h, h);
    }
}
