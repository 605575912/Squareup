package com.squareup.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.squareup.lib.R;
import com.squareup.lib.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 画廊效果
 * Created by lzx on 2017/06/21 0021.
 */

public class MindleViewPager extends RelativeLayout {
    private ViewPager viewPager;
    private boolean animator = false;
    private int pagemargin = 0;
    LinearLayout indicator;
    ViewPager.OnPageChangeListener listener;

    public MindleViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public MindleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MindleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MindleViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private List list = new ArrayList();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MindleViewPager);
            animator = a.getBoolean(R.styleable.MindleViewPager_animator, false);
            pagemargin = (int) a.getDimension(R.styleable.MindleViewPager_pagemargin, 0f);
            a.recycle();
        }

        setGravity(Gravity.CENTER);
        setClipChildren(!animator);
        viewPager = new SuperViewPager(context);
        viewPager.setClipChildren(!animator);
        viewPager.setOffscreenPageLimit(3);

        if (animator) {
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            viewPager.setPageMargin(pagemargin);
        }
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }

        });
        addView(viewPager);
        indicator = new LinearLayout(context);
        indicator.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 30;
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(indicator, params);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listener != null) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null) {
                    listener.onPageSelected(position);
                }
                if (list == null || list.size() == 0) {
                    return;
                }
                int a = position / list.size();
                if (a <= 0) {
                    a = position;
                } else {
                    a = position % list.size();
                }
                if (selected != 0 && normal != 0 && indicator.getChildCount() > 0) {
                    int pre = a - 1;
                    int apre = a + 1;
                    if (pre >= 0) {
                        setIndicator(pre, normal);
                    } else {
                        setIndicator(indicator.getChildCount() - 1, normal);
                    }
                    if (apre < indicator.getChildCount()) {
                        setIndicator(apre, normal);
                    } else {
                        setIndicator(0, normal);
                    }
                    setIndicator(a, selected);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void setPageMargin(int marginPixels) {
        if (animator) {
            viewPager.setPageMargin(marginPixels);
        }
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        if (viewPager == null) {
            return;
        }
        this.listener = listener;
    }

    Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private class MScroller extends Scroller {

        private boolean noDuration;

        private void setNoDuration(boolean noDuration) {
            this.noDuration = noDuration;
        }

        private MScroller(Context context) {
            this(context, sInterpolator);
        }

        private MScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            if (noDuration)
                super.startScroll(startX, startY, dx, dy, 0);
            else
                super.startScroll(startX, startY, dx, dy, 2000);
        }
    }

    private class ViewPageHelper {
        ViewPager viewPager;

        MScroller scroller;

        private ViewPageHelper(ViewPager viewPager) {
            this.viewPager = viewPager;
            init();
        }

        private void setCurrentItem(int item) {
            setCurrentItem(item, true);
        }

        private MScroller getScroller() {
            return scroller;
        }


        private void setCurrentItem(int item, boolean somoth) {
            int current = viewPager.getCurrentItem();
            //如果页面相隔大于1,就设置页面切换的动画的时间为0
            if (Math.abs(current - item) > 1) {
                scroller.setNoDuration(true);
                viewPager.setCurrentItem(item, somoth);
                scroller.setNoDuration(false);
            } else {
                scroller.setNoDuration(false);
                viewPager.setCurrentItem(item, somoth);
            }
        }

        private void init() {
            scroller = new MScroller(viewPager.getContext());
            Class<ViewPager> cl = ViewPager.class;
            try {
                Field field = cl.getDeclaredField("mScroller");
                field.setAccessible(true);
                //利用反射设置mScroller域为自己定义的MScroller
                field.set(viewPager, scroller);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }

    private class SuperViewPager extends ViewPager {


        private ViewPageHelper helper;

        public SuperViewPager(Context context) {
            this(context, null);
        }

        public SuperViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            helper = new ViewPageHelper(this);
        }

        @Override
        public void setCurrentItem(int item) {
            setCurrentItem(item, true);
        }

        @Override
        public void setCurrentItem(int item, boolean smoothScroll) {
            MScroller scroller = helper.getScroller();
            if (Math.abs(getCurrentItem() - item) > 1) {
                scroller.setNoDuration(true);
                super.setCurrentItem(item, smoothScroll);
                scroller.setNoDuration(false);
            } else {
                scroller.setNoDuration(false);
                super.setCurrentItem(item, smoothScroll);
            }
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (list == null) {
                return;
            }
            int max = list.size() - 1;
            if (max >= 0) {
                int i = viewPager.getCurrentItem();
                viewPager.setCurrentItem(i + 1, true);
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };
    private LunAdapter lunAdapter;

    public void setAdapter(LunAdapter lunAdapter, List list) {
        setAdapter(lunAdapter, list, 0, 0);
    }

    int selected, normal;

    public void setAdapter(LunAdapter lunAdapter, List list, int selected, int normal) {
        this.lunAdapter = lunAdapter;
        this.list = list;
        this.selected = selected;
        this.normal = normal;
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new MyPagerAdapter());
        }
        handler.removeCallbacksAndMessages(null);
        indicator.removeAllViews();
        if (list != null && list.size() > 0) {
            if (selected != 0 && normal != 0) {
                for (int i = 0; i < list.size(); i++) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setPadding(10, 0, 10, 0);
                    imageView.setImageResource(normal);
                    indicator.addView(imageView);
                }
            }
            viewPager.setCurrentItem(0);
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    public interface LunAdapter {
        View getview(ViewGroup container, int position);
    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (list == null || list.size() == 0) {
                return null;
            }
            int a = position / list.size();
            if (a <= 0) {
                a = position;
            } else {
                a = position % list.size();
            }
            if (lunAdapter != null) {
                View o = lunAdapter.getview(container, a);
                if (o != null) {
                    (container).addView(o);
                    return o;
                }
            }

            return null;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((ImageView) object);
        }
    }

    private void setIndicator(int index, int id) {
        ImageView imageView = (ImageView) indicator.getChildAt(index);
        imageView.setImageResource(id);
    }

    private static final float MAX_SCALE = 1.1999f;
    private static final float MIN_SCALE = 1.0f;//0.85f
    private static final float VIEW_SCALE = 0.5f;

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position < -1) {
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    view.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    view.setTranslationX(scaleFactor * 2);
                }
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);

            }
        }
    }

    private boolean hasparams;
    private int defaulth = 0;
    private int defaultw = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (animator && viewPager != null) {
            ViewGroup.LayoutParams parentparams = getLayoutParams();
            if (parentparams != null && !hasparams) {
                hasparams = true;
                defaulth = MeasureSpec.getSize(heightMeasureSpec);
                defaultw = MeasureSpec.getSize(widthMeasureSpec);
                int w = 0;
                int h = 0;
                if (parentparams.width != ViewGroup.LayoutParams.MATCH_PARENT && parentparams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    if (parentparams.width < defaultw) {
                        defaultw = parentparams.width;
                    }
                }
                if (parentparams.height != ViewGroup.LayoutParams.MATCH_PARENT && parentparams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    if (parentparams.height < defaulth) {
                        defaulth = parentparams.height;
                    }
                }
                h = (int) (defaulth / (MAX_SCALE));
                w = (int) (defaultw * VIEW_SCALE);
                LayoutParams params = new LayoutParams(
                        w,
                        h);
                viewPager.setLayoutParams(params);
            }
            super.onMeasure(MeasureSpec.makeMeasureSpec(defaultw, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(defaulth, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
