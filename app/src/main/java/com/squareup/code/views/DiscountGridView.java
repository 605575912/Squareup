package com.squareup.code.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by 分割线 on 2017/06/14 0014.
 */

public class DiscountGridView extends CunGridView {
    public DiscountGridView(Context context) {
        super(context);
    }

    public DiscountGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscountGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DiscountGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getNumColumns() == 0) {
            return;
        }
        int heng = getChildCount() / getNumColumns();
        int out = getChildCount() % getNumColumns();
        Paint paint = new Paint();
//        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#FFD2D1D1"));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        if (out > 0) {
            heng++;
        }
        int h = getMeasuredHeight() / heng;
        if (heng > 1) {
            for (int i = 0; i < heng; i++) {
                canvas.drawLine(0, i * h, getMeasuredWidth(), i * h, paint);
            }
        }
        int w = getMeasuredWidth() / getNumColumns();
        for (int i = 1; i < getNumColumns(); i++) {
            if (out == 0) {
                canvas.drawLine(i * w, 0, i * w, getMeasuredHeight(), paint);
            } else {
                if (i > out) {
                    canvas.drawLine(i * w, 0, i * w, getMeasuredHeight() - h, paint);
                } else {
                    canvas.drawLine(i * w, 0, i * w, getMeasuredHeight(), paint);
                }

            }
        }
    }
}
