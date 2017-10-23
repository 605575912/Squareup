package com.squareup.code.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.squareup.code.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/07/06 0006.
 */

public class AllanimationView extends SurfaceView implements SurfaceHolder.Callback {

    public AllanimationView(Context context) {
        super(context);
        init();
    }

    public AllanimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AllanimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AllanimationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    PlayThread mPlayThread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPlayed = true;
        mPlayThread = new PlayThread();
        mPlayThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (this) {
            mPlayed = false;
            mPlayThread.interrupt();
        }


    }

    SurfaceHolder mHolder;

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        setFocusable(true);
    }

    boolean mPlayed = true;
    Bitmap bitmap;

    class Bady {
        Bitmap bitmap;
        float x, y;
        int i;
        int max = 100;

        public Bady(Bitmap bitmap, int x, int y, int max) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
            this.max = max;
        }

        public void draw(Canvas canvas) {
            if (y > canvas.getHeight()) {
                return;
            }
            RectF rectf = new RectF();
            y = rectf.top = (y + 20);
            rectf.bottom = rectf.top + bitmap.getHeight();
            i++;
            if (i > max) {
                i = 1;
            }
            if (i > max / 2) {
                x = rectf.left = (x + 5);
            } else {
                x = rectf.left = (x - 5);
            }
            rectf.right = rectf.left + bitmap.getWidth();
            Paint paint = new Paint();
            canvas.drawBitmap(bitmap, null, rectf, paint);
        }
    }

    class Rain {
        Bitmap bitmap;
        float x, y;
        int i;
        boolean max = false;
        Paint paint = new Paint();

        public Rain(Bitmap bitmap, int x, int y, boolean max) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
            this.max = max;
        }

        public boolean draw(Canvas canvas) {
            if (y > canvas.getHeight()) {
                return false;
            }
            RectF rectf = new RectF();
            y = rectf.top = (y + 20);
            rectf.bottom = rectf.top + bitmap.getHeight();
            i++;
//            if (i > max) {
//                i = 1;
//            }
//            if (i > max / 2) {
            if (max) {
                x = rectf.left = (x + 5);
            } else {
                x = rectf.left = (x - 5);
            }

//            } else {
//                x = rectf.left = (x - 1);
//            }
            rectf.right = rectf.left + bitmap.getWidth();
//            paint.setDither(true);
//            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmap, null, rectf, paint);
            return true;
        }
    }

    List<Bady> list;
    List<Rain> listrains;
    Canvas canvas;

    private class PlayThread extends Thread {

        @Override
        public void run() {
            synchronized (AllanimationView.this) {
                while (mPlayed) {
                    canvas = mHolder.lockCanvas();
                    if (canvas == null) {
                        mPlayed = false;
                        break;
                    }
                    Paint paint = new Paint();
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    canvas.drawPaint(paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    }
                    if (list == null) {
                        list = new ArrayList<>();
                        for (int i = 0; i < 15; i++) {
                            Bady bady = new Bady(bitmap, (int) (Math.random() * getWidth()), (int) (Math.random() * -getHeight()), (int) (200 + Math.random() * 150));
                            list.add(bady);
                        }
                    }
                    for (Bady bady : list) {
                        bady.draw(canvas);
                    }

                    if (listrains == null) {
                        listrains = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            double r = Math.random() * 10;
                            Rain bady = new Rain(bitmap, (int) (Math.random() * getWidth()), (int) (Math.random() * -getHeight()), r > 5);
                            listrains.add(bady);
                        }
                    }
                    for (Rain bady : listrains) {
                        bady.draw(canvas);
                    }
                    try {
                        mHolder.unlockCanvasAndPost(canvas);
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }

    }
//				canvas.drawColor(Color.BLACK);
//				canvas.save();
//				int[] colors = new int[2];
//				colors[0] = -2757121;
//				colors[1] = -6433281;
//				LinearGradient linearGradient = new LinearGradient(0f, 0f,  0f,
//						0f, colors, null, Shader.TileMode.REPEAT);
//				Paint mPaint = new Paint();
//				mPaint.setShader(linearGradient);
//				canvas.drawRect(new RectF(0, 0, mWidth, mHeight), mPaint);
//				canvas.restore();

//                canvas.save();
//                mBackground.draw(canvas);
//                canvas.restore();

//                if (mIsAllowSilde) {
}
