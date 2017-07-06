package com.squareup.code.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 下划线控件
 * Created by lzx on 2017/06/23 0023.
 */

public class AutoLinkTextView extends TextView {
    private Paint paint;

    public AutoLinkTextView(Context context) {
        super(context);
        init();
    }

    public AutoLinkTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoLinkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (paint == null) {
            paint = getPaint();
            paint.setAntiAlias(true);
            int textColor = getCurrentTextColor();
            paint.setColor(textColor);
            if (Build.VERSION.SDK_INT >= 21) {
                setPadding(0, 0, 0, (int) getLineSpacingExtra());
            } else {
                setPadding(0, 0, 0, 0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoLinkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!TextUtils.isEmpty(this.getText())) {
            int h = getLineHeight();
            for (int i = 1; i <= getLineCount(); i++) {
                canvas.drawLine(0, h * i, getMeasuredWidth(), h * i, paint);
            }
        }
        super.onDraw(canvas);
    }

}
