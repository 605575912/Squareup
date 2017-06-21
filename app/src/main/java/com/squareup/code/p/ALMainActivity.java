package com.squareup.code.p;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.R;
import com.squareup.lib.view.MindleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 画廊效果
 */
public class ALMainActivity extends Activity {
    static final String TAG = "ALMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sv);
        MindleViewPager viewpager = (MindleViewPager) findViewById(R.id.viewpager);
        final List<String> list = new ArrayList<>();
        list.add("1");
        viewpager.setAdapter(new MindleViewPager.LunAdapter() {
            @Override
            public View getview(ViewGroup container, int position) {
                Log.i(TAG, list.get(position));
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(R.drawable.trip_flight_home_train_default_banner);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                return imageView;
            }
        }, list);
    }
}
