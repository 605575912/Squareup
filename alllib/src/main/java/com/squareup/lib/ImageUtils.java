package com.squareup.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.security.MessageDigest;


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

    public static void loadCircleImage(Context context, String url, ImageView imageView, int defaultResId) {
        if (defaultResId == 0) {
            if (TextUtils.isEmpty(url)) {
                imageView.setImageResource(defaultResId);
                return;
            }
            Glide.with(context).load(url).into(imageView);
        } else {
            try {
                Drawable drawable = context.getResources().getDrawable(defaultResId);
                loadCircleImage(context, url, imageView, drawable);
            } catch (Exception e) {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                Glide.with(context).load(url).into(imageView);
            }

        }
    }

    public static void loadRoundImage(Context context, String url, ImageView imageView, int defaultResId) {
        if (defaultResId == 0) {
            if (TextUtils.isEmpty(url)) {
                imageView.setImageResource(defaultResId);
                return;
            }
            Glide.with(context).load(url).into(imageView);
        } else {
            try {
                Drawable drawable = context.getResources().getDrawable(defaultResId);
                loadRoundImage(context, url, imageView, drawable);
            } catch (Exception e) {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                Glide.with(context).load(url).into(imageView);
            }

        }
    }

    public static void loadRoundImage(Context context, String url, final ImageView imageView, Drawable drawable) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(drawable);
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(drawable)
                .error(drawable)
                .priority(Priority.HIGH).dontAnimate().transform(new RoundTransform(20, imageView));
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView, Drawable drawable) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(drawable);
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(drawable)
                .error(drawable)
                .priority(Priority.HIGH).dontAnimate().transform(new GlideCircleTransform(context));
        Glide.with(context).load(url).apply(options).into(imageView);
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
                .priority(Priority.HIGH).dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    //    public static void loadImageAsBitmap(Context context, String url, ImageView imageView, int defaultResId) {
//        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(defaultResId).into(imageView);
//    }
    static class RoundTransform extends BitmapTransformation {
        int radius;
        ImageView imageView;

        public RoundTransform(int radius, ImageView imageView) {
            this.radius = radius;
            this.imageView = imageView;
        }

        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(toTransform, outWidth, outHeight);
        }

        private Bitmap circleCrop(Bitmap source, int outWidth, int outHeight) {
            if (source == null) return null;
            if (imageView != null) {
                outHeight = imageView.getMeasuredHeight();
                outWidth = imageView.getMeasuredWidth();
            }
            Bitmap result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            int sourcewidth = source.getWidth();
            int sourceheight = source.getHeight();
            float sale = sourcewidth * 1.0f / sourceheight;
            float outsale = outWidth * 1.0f / outHeight;
            float destwidth;
            float destheight;
            Rect srcR = new Rect(0, 0, 0, 0);
            if (outsale > sale) {
                destheight = (sourcewidth / outsale);
                srcR.right = sourcewidth;
                srcR.top = (int) ((sourceheight - destheight) / 2);
                srcR.bottom = srcR.top + (int) destheight;
            } else {
                destwidth = (sourceheight * outsale);
                srcR.left = (int) ((sourcewidth - destwidth) / 2);
                srcR.right = srcR.left + (int) destwidth;
                srcR.bottom = sourceheight;
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            RectF rect = new RectF(0, 0, outWidth, outHeight);
            canvas.drawRoundRect(rect, radius, radius, paint);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, srcR, rect, paint);
            return result;
        }


        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }


    }

    static class GlideCircleTransform extends BitmapTransformation {

        private Paint mBorderPaint;
        private float mBorderWidth;

        public GlideCircleTransform(Context context) {
        }

        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
//            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
            mBorderWidth = borderWidth;

            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }


        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            if (mBorderPaint != null) {
                float borderRadius = r - mBorderWidth / 2;
                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
            }
            return result;
        }


        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }


    }
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
