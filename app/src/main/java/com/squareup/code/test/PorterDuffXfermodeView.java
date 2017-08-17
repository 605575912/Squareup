package com.squareup.code.test;

/**
 * Created by Administrator on 2017/08/14 0014.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Alex Pang on 2016/8/20.
 * 自定义View，使用PorterDuff.Mode验证图像合成效果
 */
public class PorterDuffXfermodeView extends View {
    Paint paint = new Paint();
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.MULTIPLY;

    public PorterDuffXfermodeView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        //背景色设为白色，方便比较效果
//        canvas.drawColor(Color.WHITE);
//        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
//        int saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);
//        //绘制目标图
//        Bitmap dstBmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        Bitmap srcBmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        canvas.drawBitmap(dstBmp, null, dstRect, mPaint);
//        //设置混合模式
//        mPaint.setXfermode(mXfermode);
//        //绘制源图
//        canvas.drawBitmap(srcBmp, null, srcRect, mPaint);
//        //清除混合模式
//        mPaint.setXfermode(null);
//        //还原画布
//        canvas.restoreToCount(saveCount);
        RectF rect = new RectF(30, 30, 300, 300);
        canvas.drawColor(Color.YELLOW);
        int saveCount = canvas.saveLayer(rect, paint, Canvas.ALL_SAVE_FLAG);

//        paint.setColor(Color.WHITE); 
        Bitmap resulat = getABitmap();

        canvas.drawBitmap(resulat, null, rect, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Bitmap result = getBitmap();
//        Rect srcR = new Rect(0, 0, result.getWidth(), result.getHeight());
        RectF recta = new RectF(30, 30, 300, 300);
        canvas.drawBitmap(result, null, recta, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

    Bitmap getBitmap() {
        Bitmap result = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(result);
        Paint bitp = new Paint();
        bitp.setColor(Color.GREEN);
        RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
        canvas1.drawRoundRect(rect, 60, 60, bitp);
        return result;
    }

    Bitmap getABitmap() {
        Bitmap result = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(result);
        Paint bitp = new Paint();
        bitp.setColor(Color.RED);
        RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
        canvas1.drawRoundRect(rect, 0, 0, bitp);
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        int width = w <= h ? w : h;
//        int centerX = w / 2;
//        int centerY = h / 2;
//        int quarterWidth = width / 4;
//        srcRect = new RectF(centerX - quarterWidth, centerY - quarterWidth, centerX + quarterWidth, centerY + quarterWidth);
//        dstRect = new RectF(centerX - quarterWidth, centerY - quarterWidth, centerX + quarterWidth, centerY + quarterWidth);
    }
}

