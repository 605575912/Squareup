package com.squareup.code.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.code.R;


/**
 * Created by Administrator on 2017/04/17 0017.
 */

public class RechargeTitle extends ViewGroup {
    TextView tv_content;
    TextView tv_title;

    public RechargeTitle(Context context) {
        super(context);
        init(context);
    }

    public RechargeTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RechargeTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = 21)
    public RechargeTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int bottom = (int) getResources().getDimension(R.dimen.meilititle_bottom);
        tv_title.layout(0, (int) getResources().getDimension(R.dimen.title_top), tv_title.getMeasuredWidth(), (int) getResources().getDimension(R.dimen.title_top) + tv_title.getMeasuredHeight());
        int con_top = getMeasuredHeight() - tv_content.getMeasuredHeight() - bottom;
        int con_left = (getMeasuredWidth() - tv_content.getMeasuredWidth()) / 2;
        tv_content.layout(con_left, con_top, con_left + tv_content.getMeasuredWidth(), con_top + tv_content.getMeasuredHeight());
        Rect rect = new Rect();
        tv_content.getPaint().getTextBounds("1", 0, 1, rect);
        int db = (tv_content.getMeasuredHeight() - rect.height()) / 2;
        int iv_top = tv_content.getBottom() - imageView.getMeasuredHeight() - db;
        int left_margn = (int) getResources().getDimension(R.dimen.title_image_left);
        imageView.layout(con_left - imageView.getMeasuredWidth() - left_margn, iv_top, con_left, iv_top + imageView.getMeasuredHeight());
    }

    void init(Context context) {
        setBackgroundColor(Color.RED);
        tv_title = new TextView(context);
        tv_title.setText(getResources().getString(R.string.recharge_m));
        tv_title.setTextColor(getResources().getColor(R.color.bg_window));
        LayoutParams paramst = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setTextSize(16);
        addView(tv_title, paramst);
        tv_content = new TextView(context);
        tv_content.setTextColor(getResources().getColor(R.color.bg_window));
        tv_content.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tv_content.setTextSize(52);
        tv_content.setSingleLine();
        tv_content.setText("99999990000000000000");
        LayoutParams paramsc = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(tv_content, paramsc);
        imageView = new ImageView(getContext());
        LayoutParams paramsi = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setImageResource(R.mipmap.ico_05_balance);
        addView(imageView, paramsi);
    }

    ImageView imageView;

    public void setText(long meili) {
        if (meili <= 0) {
            tv_content.setText("0");
        } else {
            tv_content.setText(String.valueOf(meili));
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int with = MeasureSpec.getSize(widthMeasureSpec);
        tv_title.measure(widthMeasureSpec, heightMeasureSpec);
        int tv_w = (int) tv_content.getPaint().measureText(tv_content.getText().toString());
        float maxw = with * 0.8f;
        if (tv_w > maxw) {
            int t_w = (int) tv_content.getPaint().measureText(tv_content.getText().toString().substring(0,1));
            Log.i("TAG", "===DEV==" + t_w);
            tv_w = (int) (maxw);
        }
        tv_content.measure(MeasureSpec.makeMeasureSpec(tv_w, MeasureSpec.EXACTLY), heightMeasureSpec);
        imageView.measure(MeasureSpec.makeMeasureSpec((int) getResources().getDimension(R.dimen.title_image), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int) getResources().getDimension(R.dimen.title_image), MeasureSpec.EXACTLY));
        setMeasuredDimension(with, MeasureSpec.makeMeasureSpec((int) getResources().getDimension(R.dimen.title_height), MeasureSpec.EXACTLY));

    }

    public void setTitleText(CharSequence text) {
        tv_title.setText(text);
    }
}
