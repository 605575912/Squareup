package com.squareup.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;


public class ImageUtils {

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, 0);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int defaultResId) {

        if (defaultResId == 0) {
            if (TextUtils.isEmpty(url)) {
                imageView.setImageResource(defaultResId);
                return;
            }
            Glide.with(context).load(url).into(imageView);
        } else {
            try {
                Drawable drawable = context.getResources().getDrawable(defaultResId);
                loadImage(context, url, imageView, drawable);
            } catch (Exception e) {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                Glide.with(context).load(url).into(imageView);
            }

        }
    }

    public static void loadImage(Context context, String url, ImageView imageView, Drawable drawable) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(drawable);
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(drawable)
                .error(drawable)
                .priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
//    public static void loadImageAsBitmap(Context context, String url, ImageView imageView, int defaultResId) {
//        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(defaultResId).into(imageView);
//    }


//    /**
//     * 加载带尺寸的图片
//     *
//     * @param context
//     * @param url
//     * @param width
//     * @param height
//     * @param imageView
//     */
//    public static void loadImageWithSize(Context context, String url, int width, int height, ImageView imageView) {
//        Glide.with(context).load(url).override(width, height).into(imageView);
//    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImageWithLocation(Context context, Integer path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

//    /**
//     * 圆形加载
//     *
//     * @param context
//     * @param url
//     * @param imageview
//     */
//    public static void loadCircleImage(Context context, String url, ImageView imageview, int defaultResId) {
//        Glide.with(context).load(url).centerCrop().placeholder(defaultResId)
//                .transform(new GlideCircleTransform(context)).into(imageview);
//
//    }

//    /**
//     * 圆形加载
//     *
//     * @param context
//     * @param url
//     * @param imageview
//     */
//    public static void loadCircleImageWithBorder(Context context, String url, ImageView imageview, int defaultResId, int borderWidth, int borderColor) {
//        Glide.with(context).load(url).centerCrop().placeholder(defaultResId)
//                .transform(new GlideCircleTransform(context, borderWidth, borderColor)).into(imageview);
//    }

//    static class GlideCircleTransform extends BitmapTransformation {
//
//        private Paint mBorderPaint;
//        private float mBorderWidth;
//
//        public GlideCircleTransform(Context context) {
//            super(context);
//        }
//
//        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
//            super(context);
////            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
//            mBorderWidth = borderWidth;
//
//            mBorderPaint = new Paint();
//            mBorderPaint.setDither(true);
//            mBorderPaint.setAntiAlias(true);
//            mBorderPaint.setColor(borderColor);
//            mBorderPaint.setStyle(Paint.Style.STROKE);
//            mBorderPaint.setStrokeWidth(mBorderWidth);
//        }
//
//
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return circleCrop(pool, toTransform);
//        }
//
//        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//            if (source == null) return null;
//
//            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//            // TODO this could be acquired from the pool too
//            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//            if (result == null) {
//                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//            }
//            Canvas canvas = new Canvas(result);
//            Paint paint = new Paint();
//            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//            paint.setAntiAlias(true);
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//            if (mBorderPaint != null) {
//                float borderRadius = r - mBorderWidth / 2;
//                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
//            }
//            return result;
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName();
//        }
//    }
}
