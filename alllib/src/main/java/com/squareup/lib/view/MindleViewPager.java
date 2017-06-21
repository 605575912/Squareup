package com.squareup.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 画廊效果
 * Created by lzx on 2017/06/21 0021.
 */

public class MindleViewPager extends RelativeLayout {
    private ViewPager viewPager;

    public MindleViewPager(Context context) {
        super(context);
        init(context);
    }

    public MindleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MindleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MindleViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private List list = new ArrayList();

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setClipChildren(false);
        viewPager = new SuperViewPager(context);
        addView(viewPager);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setPageMargin(200);
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }

        });
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (viewPager == null) {
            return;
        }
        viewPager.setOnPageChangeListener(listener);
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
        this.lunAdapter = lunAdapter;
        this.list = list;
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
        if (viewPager != null) {
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
                h = (int) (defaulth * (2 - MAX_SCALE));
                w = (int) (defaultw * VIEW_SCALE);
                LayoutParams params = new LayoutParams(
                        w,
                        h);
                viewPager.setLayoutParams(params);
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(defaultw, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(defaulth, MeasureSpec.EXACTLY));
    }
}
