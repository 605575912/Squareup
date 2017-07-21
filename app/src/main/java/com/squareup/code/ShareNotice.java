package com.squareup.code;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.code.wxapi.WXEntryActivity;
import com.squareup.lib.BaseApplication;
import com.squareup.lib.utils.AppLibUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by lzx on 2017/06/26 0026.
 */

public class ShareNotice implements View.OnClickListener {
    private static Object o = new Object();
    private float temp;
    private RelativeLayout ret_bg;
    private float ty;
    private IWXAPI iwxapi;
    private WindowManager wm;
    private View v;
    private int status;
    private long duration = 800;
    boolean ishideing = false;
    private WindowManager.LayoutParams params;

    private static ShareNotice shareNotice;

    private ShareNotice() {
    }

    public synchronized static ShareNotice getInstance() {
        if (shareNotice == null) {
            synchronized (o) {
                shareNotice = new ShareNotice();
            }
        }
        return shareNotice;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                ishideing = true;
                temp = 0;
                if (v.getY() < 0) {
                    return;
                }
                ValueAnimator animator = new ValueAnimator();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        if (temp != value) {
                            temp = value;
                            v.setTranslationY(value);
                            if (temp == -params.height) {
                                try {
                                    wm.removeView(v);
                                } catch (Exception e) {

                                }

                                ishideing = false;
                            }
                        }
                    }
                });
                animator.setDuration(duration);
                animator.setFloatValues(v.getY(), -params.height);
                animator.start();

            }
        }
    };

    public void show(Context context) {
        temp = 0;
        v = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                PixelFormat.TRANSLUCENT);
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

//        int sdk = Integer.parseInt(Build.VERSION.SDK);
//        if (sdk < 19){ //Android 4.3之前的版本的TYPE_TOAST不支持响应TOUCH事件，会导致浮窗不能点击、拖动
//            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        }
        params.flags = params.flags | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        params.flags = params.flags | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        params.alpha = 1.0f;
        params.gravity = Gravity.TOP;
        params.x = 0;
        status = AppLibUtils.getStatusBarHeight();
        params.height = (int) (context.getResources().getDimension(R.dimen.share_notiecH) + status);
        final ImageView share_wx = (ImageView) v.findViewById(R.id.share_wx);
        final ImageView share_py = (ImageView) v.findViewById(R.id.share_py);
        final ImageView share_qq = (ImageView) v.findViewById(R.id.share_qq);
        final ImageView share_sin = (ImageView) v.findViewById(R.id.share_sin);
        final ImageView share_zone = (ImageView) v.findViewById(R.id.share_zone);
        ret_bg = (RelativeLayout) v.findViewById(R.id.ret_bg);
        LinearLayout.LayoutParams retparams = (LinearLayout.LayoutParams) ret_bg.getLayoutParams();
        retparams.topMargin = status;
        ret_bg.setLayoutParams(retparams);
        v.setTranslationY(-params.height);
        share_wx.setOnClickListener(this);
        share_py.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_sin.setOnClickListener(this);
        share_zone.setOnClickListener(this);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ty = event.getY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (event.getY() - ty < -50 && !ishideing) {
                            ishideing = true;
                            handler.sendEmptyMessage(0);
                        }
                        break;
                    }

                }

                return false;
            }
        });
        wm.addView(v, params);
        ValueAnimator animator = new ValueAnimator();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (temp != value) {
                    temp = value;
                    v.setTranslationY(value);
                    if (temp == 0) {
                        handler.sendEmptyMessageDelayed(0, 5100);
                    }
                }
            }
        });
        animator.setDuration(duration);
        animator.setFloatValues(-params.height, 0);
        animator.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_wx: {

                break;
            }
            case R.id.share_py: {
                iwxapi = create();
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "https://www.baidu.com";
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "title";
                msg.description = "des";
                final int THUMB_SIZE = 150;
                Bitmap bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.btn_back_normal);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
                break;
            }
            case R.id.share_qq: {
                break;
            }
            case R.id.share_sin: {
                break;
            }
            case R.id.share_zone: {
                break;
            }
        }
    }

    private IWXAPI create() {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(BaseApplication.getApplication(), WXEntryActivity.APP_ID, true);
            iwxapi.registerApp(WXEntryActivity.APP_ID);
        }
        return iwxapi;
    }

    public byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
