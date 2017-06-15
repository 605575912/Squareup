package com.squareup.code.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/06/15 0015.
 */

public class PriceTextView extends TextView {
    private Paint paint;

    public PriceTextView(Context context) {
        super(context);
        init();
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (paint == null) {
            paint = getPaint();
            paint.setAntiAlias(true);
            int textColor = getCurrentTextColor();
            paint.setColor(textColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(this.getText())) {
            super.onDraw(canvas);
        } else {
            canvas.drawText(getText().toString(), 0, getMeasuredHeight() - 5, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect rect = new Rect();
        CharSequence text = getText();
        if (text.length() == 0) {
            text = "1";
        }
        paint.getTextBounds(text.toString(), 0, text.length(), rect);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (rect.height() + 5), MeasureSpec.EXACTLY));
    }
}
