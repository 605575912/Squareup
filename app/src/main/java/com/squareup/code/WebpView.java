package com.squareup.code;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by lzx on 2017/08/03 0003.
 */

public class WebpView extends SimpleDraweeView {
    private Paint paint = new Paint();
    private RectF rect;
    private Bitmap resulat, result;
    private float radius;

    public WebpView(Context context) {
        super(context);
        init();

    }

    public WebpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        radius = Resources.getSystem().getDisplayMetrics().density * 4;
        paint.setAntiAlias(true);
    }

    public void setAntion(int visibility) {
        if (hasController()) {
            if (getController().getAnimatable() != null) {
                if (visibility == VISIBLE) {
                    if (!getController().getAnimatable().isRunning()) {
                        getController().getAnimatable().start();
                    }
                } else {
                    if (getController().getAnimatable().isRunning()) {
                        getController().getAnimatable().stop();
                    }
                }
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        rect = new RectF(0, 0, w, h);
        resulat = getBeforeitmap(w, h, 0, 0);
        result = getBeforeitmap(w, h, radius, radius);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        long time = System.currentTimeMillis();
//        int saveCount = canvas.saveLayer(rect, paint, Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(resulat, null, rect, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
//        canvas.drawBitmap(result, null, rect, paint);
//        paint.setXfermode(null);
//        canvas.restoreToCount(saveCount);
//        Log.i("TAG", "===" + (System.currentTimeMillis() - time));
    }

    private Bitmap getBeforeitmap(int w, int h, float rx, float ry) {
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas canvas1 = new Canvas(result);
        Paint bitp = new Paint();
        bitp.setColor(Color.WHITE);
        RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
        canvas1.drawRoundRect(rect, rx, ry, bitp);
        return result;
    }

    public void setUri(String url) {
        setUri(url, true);
    }

    public void setUri(String url, boolean auto) {
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setResizeOptions(new ResizeOptions(5, 10)).build();
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if (hierarchy != null) {
//            RoundingParams roundingParams = new RoundingParams();
//            roundingParams.setCornersRadius(Resources.getSystem().getDisplayMetrics().density * radius);
//            hierarchy.setRoundingParams(roundingParams);

            RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);//这个方法在某些情况下无法成圆,比如gif
            roundingParams.setOverlayColor(Color.WHITE);//加一层遮罩,这个是关键方法
            hierarchy.setRoundingParams(roundingParams);
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
//                .setImageRequest(request)
//                .setOldController(getController())
                .setAutoPlayAnimations(auto)
                .build();
        setController(controller);
    }
}
