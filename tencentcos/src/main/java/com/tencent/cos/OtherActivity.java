package com.tencent.cos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.cos.sample.BizServer;
import com.tencent.cos.sample.BucketSamples;


/**
 * Created by bradyxiao on 2016/9/13.
 */
public class OtherActivity extends Activity implements View.OnClickListener {

        private Button bucketBtn;
        private TextView detailText;
        BizServer bizServer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_other);
                bucketBtn = (Button) findViewById(R.id.bucket);
                detailText = (TextView) findViewById(R.id.detail);
                bucketBtn.setOnClickListener(this);
                bizServer = BizServer.getInstance(getApplicationContext());
        }

        @Override
        public void onClick(View v) {
                int id = v.getId();
                if (R.id.bucket == id) {
                        statBucket();
                        return;
                }
        }

        public void statBucket() {
                BucketSamples bucketSamples = new BucketSamples(detailText);
                bucketSamples.execute(bizServer);
        }
}