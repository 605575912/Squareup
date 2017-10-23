package com.squareup.code;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.lib.view.CustomImageView;

/**
 * Created by liangzhenxiong on 2017/10/22.
 */

public class ImageViewActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final CustomImageView customImageView = new CustomImageView(this);
//        customImageView.setImageDrawable(getDrawable(R.drawable.pnl_03_room_yonghu_upgrade));
        setContentView(customImageView);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("http://n.sinaimg.cn/news/transform/20171017/R8v5-fymvkax7491357.jpg")).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(final Bitmap bitmap) {
                if (bitmap == null) {
                    onFailureImpl(null);
                    return;
                }
                customImageView.setImageBitmap(bitmap);


            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
//        ImageUtils.loadImage("http://n.sinaimg.cn/news/transform/20171017/R8v5-fymvkax7491357.jpg", customImageView);
    }
}
