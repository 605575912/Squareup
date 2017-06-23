package com.lcodecore.tkrefreshlayout.footer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

/**
 * Created by lcodecore on 2017/3/7.
 */

public class IPulseView extends View implements IBottomView {

    public static final int DEFAULT_SIZE = 50; //dp
    private float circleSpacing;
    TwinklingRefreshLayout refreshLayout;
    private float[] scaleFloats = new float[]{1f, 1f, 1f};

    //    private ArrayList<ValueAnimator> mAnimators;
//    private Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();
    private Paint mPaint;

    public IPulseView(Context context) {
        this(context, null);
    }

    public IPulseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IPulseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int default_size = DensityUtil.dp2px(context, DEFAULT_SIZE);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, default_size, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        setLayoutParams(params);

        circleSpacing = DensityUtil.dp2px(context, 4);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



    }

    public void setIndicatorColor(int color) {
        mPaint.setColor(color);
    }

    private int normalColor = 0xffeeeeee;
    private int animatingColor = 0xffe75946;

    public void setNormalColor(@ColorInt int color) {
        normalColor = color;
    }

    public void setAnimatingColor(@ColorInt int color) {
        animatingColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < scaleFloats.length; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        if (mAnimators != null) for (int i = 0; i < mAnimators.size(); i++) {
//            mAnimators.get(i).cancel();
//        }
    }

//    public void startAnim() {
//        if (mAnimators == null) createAnimators();
//        if (mAnimators == null) return;
//        if (isStarted()) return;
//
//        for (int i = 0; i < mAnimators.size(); i++) {
//            ValueAnimator animator = mAnimators.get(i);
//
//            //when the animator restart , add the updateListener again because they was removed by animator stop .
//            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
//            if (updateListener != null) {
//                animator.addUpdateListener(updateListener);
//            }
//            animator.start();
//        }
//        setIndicatorColor(animatingColor);
//    }

    public void stopAnim() {
//        if (mAnimators != null) {
//            for (ValueAnimator animator : mAnimators) {
//                if (animator != null && animator.isStarted()) {
//                    animator.removeAllUpdateListeners();
//                    animator.end();
//                }
//            }
//        }
//        setIndicatorColor(normalColor);
    }

//    private boolean isStarted() {
//        for (ValueAnimator animator : mAnimators) {
//            return animator.isStarted();
//        }
//        return false;
//    }

//    private void createAnimators() {
//        mAnimators = new ArrayList<>();
//        int[] delays = new int[]{120, 240, 360};
//        for (int i = 0; i < delays.length; i++) {
//            final int index = i;
//
//            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);
//
//            scaleAnim.setDuration(750);
//            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
//            scaleAnim.setStartDelay(delays[i]);
//
//            mUpdateListeners.put(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    scaleFloats[index] = (float) animation.getAnimatedValue();
//                    postInvalidate();
//                }
//            });
//            mAnimators.add(scaleAnim);
//        }
//    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        Log.i("TAG", fraction + "=" + maxHeadHeight + "=" + headHeight + "=" + fraction * headHeight);
        stopAnim();
    }

    public void setRefreshLayout(TwinklingRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
//        startAnim();
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        Log.i("TAG==", fraction + "=onPullReleasing");
        stopAnim();
    }

    @Override
    public void onFinish() {
        stopAnim();
    }

    @Override
    public void reset() {
        stopAnim();
    }
}