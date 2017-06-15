package com.squareup.code.detail;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.code.MyApplication;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.utils.LoadEmptyViewControl;
import com.squareup.code.wx.Util;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/06/15 0015.
 */

public class ActivityDetailActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    LoadEmptyViewControl loadEmptyViewControl;
    IWXAPI iwxapi;

    @Override
    protected boolean isTranslucentStatus() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        loadEmptyViewControl = new LoadEmptyViewControl(getActivity());
        loadEmptyViewControl.addLoadView(frameLayout);
        TextView backtext = (TextView) findViewById(R.id.backtext);
    }

    @Override
    public void onEventMain(EventMainObject event) {
    }

    private ShareItem create(String name, int imgid) {
        ShareItem shareItem = new ShareItem();
        shareItem.setImgid(imgid);
        shareItem.setText(name);
        return shareItem;
    }

    boolean istext = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back: {
                finish();
                break;
            }
            case R.id.share: {
                istext = false;
                Dialog dialog = new Dialog(this, R.style.ThemeDialog);
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null, false);
                dialog.setContentView(view);
                GridView grid = (GridView) view.findViewById(R.id.grid);
                List<ShareItem> list = new ArrayList<ShareItem>();
                list.add(create("微信好友", R.drawable.share_ic_base_share_weixin));
                list.add(create("朋友圈", R.drawable.share_ic_base_share_weixin_friends));
                list.add(create("QQ", R.drawable.share_ic_base_share_qq));
                list.add(create("Qzone", R.drawable.share_ic_base_share_qzone));
                ListGridAdapter adapter = new ListGridAdapter(list);
                grid.setNumColumns(4);
                grid.setAdapter(adapter);
                dialog.show();
                break;
            }
            case R.id.favorite: {
                istext = true;
                Dialog dialog = new Dialog(this, R.style.ThemeDialog);
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null, false);
                dialog.setContentView(view);
                GridView grid = (GridView) view.findViewById(R.id.grid);
                List<ShareItem> list = new ArrayList<ShareItem>();
                list.add(create("微信好友", R.drawable.share_ic_base_share_weixin));
                list.add(create("朋友圈", R.drawable.share_ic_base_share_weixin_friends));
                list.add(create("QQ", R.drawable.share_ic_base_share_qq));
                list.add(create("Qzone", R.drawable.share_ic_base_share_qzone));
                ListGridAdapter adapter = new ListGridAdapter(list);
                grid.setNumColumns(4);
                grid.setAdapter(adapter);
                dialog.show();
                break;
            }
            case R.id.more: {
                break;
            }
        }
    }

    String descontent;
    String content;
    String detitle;
    String deurl;
    private static final int THUMB_SIZE = 150;

    class ListGridAdapter extends BaseAdapter {
        List<ShareItem> list;

        public ListGridAdapter(List<ShareItem> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            ListGridAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
                holder = new ListGridAdapter.ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.tx_ = (TextView) convertView.findViewById(R.id.tx_);
                convertView.setTag(holder); //绑定ViewHolder对象
            } else {
                holder = (ListGridAdapter.ViewHolder) convertView.getTag(); //取出ViewHolder对象
            }
            final ShareItem shareItem = list.get(position);
            holder.tx_.setText(shareItem.getText());
            try {
                holder.image.setImageResource(shareItem.getImgid());
            } catch (Exception e) {
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shareItem.getImgid() == R.drawable.share_ic_base_share_weixin || shareItem.getImgid() == R.drawable.share_ic_base_share_weixin_friends) {
                        final IWXAPI iwxapi = create();
                        if (istext) {
                            final Dialog dialog = new Dialog(ActivityDetailActivity.this, R.style.ThemeDialog);
                            Display display = ActivityDetailActivity.this.getWindowManager().getDefaultDisplay();
                            int width = display.getWidth();
                            int height = display.getHeight();
                            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
                            View view = LayoutInflater.from(ActivityDetailActivity.this).inflate(R.layout.dialog_txt, parent, false);
                            dialog.setContentView(view, layoutParams);
                            final EditText edit = (EditText) view.findViewById(R.id.edit);
                            if (TextUtils.isEmpty(descontent)) {
                                edit.setText(content);
                            } else {
                                if (!TextUtils.isEmpty(content)) {
                                    edit.setText(content);
                                } else {
                                    edit.setText(descontent);
                                }
                            }
                            Button button = (Button) view.findViewById(R.id.button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    WXTextObject textObj = new WXTextObject();
                                    textObj.text = edit.getText().toString();
                                    if (TextUtils.isEmpty(textObj.text)) {
                                        ToastUtils.showToast("没填写几把");
                                        return;
                                    }
                                    WXMediaMessage msg = new WXMediaMessage();
                                    msg.mediaObject = textObj;
                                    descontent = msg.description = textObj.text;
                                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                                    req.transaction = buildTransaction("text"); //
                                    req.message = msg;
                                    req.scene = shareItem.getImgid() == R.drawable.share_ic_base_share_weixin ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                                    iwxapi.sendReq(req);
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        } else {
                            final Dialog dialog = new Dialog(ActivityDetailActivity.this, R.style.ThemeDialog);
                            Display display = ActivityDetailActivity.this.getWindowManager().getDefaultDisplay();
                            int width = display.getWidth();
                            int height = display.getHeight();
                            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
                            View view = LayoutInflater.from(ActivityDetailActivity.this).inflate(R.layout.dialog_web, parent, false);
                            dialog.setContentView(view, layoutParams);
                            dialog.show();

                            final EditText edit = (EditText) view.findViewById(R.id.edit);
                            if (TextUtils.isEmpty(descontent)) {
                                edit.setText(content);
                            } else {
                                if (!TextUtils.isEmpty(content)) {
                                    edit.setText(content);
                                } else {
                                    edit.setText(descontent);
                                }
                            }


                            Button button = (Button) view.findViewById(R.id.button);
                            final EditText title = (EditText) view.findViewById(R.id.title);
                            final EditText url = (EditText) view.findViewById(R.id.url);
                            url.setText(deurl);
                            title.setText(detitle);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(url.getText().toString())) {
                                        ToastUtils.showToast("没填写跳转网址");
                                        return;
                                    }
                                    if (TextUtils.isEmpty(title.getText().toString())) {
                                        ToastUtils.showToast("没填写标题");
                                        return;
                                    }
                                    if (TextUtils.isEmpty(edit.getText().toString())) {
                                        ToastUtils.showToast("没填写几把");
                                        return;
                                    }
                                    WXWebpageObject webpage = new WXWebpageObject();
                                    webpage.webpageUrl = deurl = url.getText().toString();
                                    WXMediaMessage msg = new WXMediaMessage(webpage);
                                    detitle = msg.title = title.getText().toString();
                                    content = msg.description = edit.getText().toString();
                                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                                    bmp.recycle();
                                    msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                                    req.transaction = buildTransaction("webpage");
                                    req.message = msg;
                                    req.scene = shareItem.getImgid() == R.drawable.share_ic_base_share_weixin ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                                    iwxapi.sendReq(req);
                                    dialog.cancel();
                                }
                            });

                        }

//


                    }
                }
            });

//            ImageUtils.loadImage(parent.getContext(), shareItem.getImg(), holder.image, R.drawable.placeholder_error);
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView tx_;
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private IWXAPI create() {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(MyApplication.application, "wx4c726304342b910d", true);
            iwxapi.registerApp("wx4c726304342b910d");
        }
        return iwxapi;
    }
}
