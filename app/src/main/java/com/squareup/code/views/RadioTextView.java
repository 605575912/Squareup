package com.squareup.code.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/06/24 0024.
 */

public class RadioTextView extends TextView {
    public RadioTextView(Context context) {
        super(context);
    }

    public RadioTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RadioTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    Paint paint, dpaint;
    float time;

    public void setTime(final int duration) {
        if (duration <= 0) {
            return;
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0, duration);
        valueAnimator.setDuration(duration * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value != time) {
                    time = value;
                    sweepAngle = 360.f / duration * value;
                    invalidate();
                }
            }
        });
        valueAnimator.start();
    }

    float sweepAngle;

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#FFAC2D2D"));
            dpaint = new Paint();
            dpaint.setStrokeWidth(10);
            dpaint.setStyle(Paint.Style.STROKE);
            dpaint.setColor(Color.parseColor("#FFD4D3D3"));
        }
        RectF oval = new RectF(10, 10, getWidth() - 10, getHeight() - 10);
        canvas.drawArc(oval, 0, 360, false, dpaint);
        canvas.drawArc(oval, 0, sweepAngle, false, paint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Paint paint = getPaint();
        Rect rect = new Rect();
        paint.getTextBounds(getText().toString(), 0, getText().length(), rect);
        setMeasuredDimension(rect.width() + 50, rect.width() + 50);
    }
}
