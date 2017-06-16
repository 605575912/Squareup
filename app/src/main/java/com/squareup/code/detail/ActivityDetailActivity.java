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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.squareup.code.Card;
import com.squareup.code.CardUnit;
import com.squareup.code.DataUnit;
import com.squareup.code.ItemData;
import com.squareup.code.MineItemData;
import com.squareup.code.MyApplication;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.baidu.LocationService;
import com.squareup.code.column.ColumnData;
import com.squareup.code.column.ColumnView;
import com.squareup.code.home.ChangedItemView;
import com.squareup.code.home.DoubleItemView;
import com.squareup.code.home.ItemView;
import com.squareup.code.home.PushItemView;
import com.squareup.code.home.banner.BannerModel;
import com.squareup.code.home.banner.BannerView;
import com.squareup.code.home.tab.LineView;
import com.squareup.code.mine.LoginCardView;
import com.squareup.code.mine.MineCard;
import com.squareup.code.mine.MineCardUnit;
import com.squareup.code.mine.MineItemView;
import com.squareup.code.mine.MineLineView;
import com.squareup.code.mine.MineSpaceView;
import com.squareup.code.utils.LoadEmptyViewControl;
import com.squareup.code.wx.Util;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;
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
    RecyclerViewAdapter adapter;
    FrameLayout frameLayout;
    LoadEmptyViewControl loadEmptyViewControl;
    IWXAPI iwxapi;
    List<BaseViewItem> list;
    private LocationService locationService;

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
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        TextView backtext = (TextView) findViewById(R.id.backtext);
        HttpUtils.getInstance(getActivity().getApplication()).getAsynMainHttp("file:///android_asset/my.txt", DataUnit.class);//返回根据JSON解析的对象

        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
//        startLocation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
//                    locationService.start();// 定位SDK
//                    // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//                    startLocation.setText(getString(R.string.stoplocation));
//                } else {
//                    locationService.stop();
//                    startLocation.setText(getString(R.string.startlocation));
//                }
//            }
//        });


    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                LogUtil.i(sb.toString());
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }


    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getData() instanceof DataUnit) {
            loadEmptyViewControl.loadcomplete();

            DataUnit dataUnit = (DataUnit) event.getData();
//            if (dataUnit.getTitletype() > 0) {
////                addTitleView(dataUnit.getTitletype());
//            }
            if (dataUnit.getDetailcards() != null) {
                for (DetailCard detailCard : dataUnit.getDetailcards()) {

                    if (detailCard.getImagedata() != null) {
                        BaseViewItem baseViewItem = new DetailImageView(ActivityDetailActivity.this, detailCard.getImagedata());
                        list.add(baseViewItem);
                    }
                    if (detailCard.getCommentdata() != null) {
                        BaseViewItem baseViewItem = new DetailCommentView(ActivityDetailActivity.this, detailCard.getCommentdata());
                        list.add(baseViewItem);
                        if (detailCard.getCommentdata().getComents() != null && detailCard.getCommentdata().getComents().length > 0) {
                            LineView b = new LineView();
                            list.add(b);
                            BaseViewItem baseViewItem = new DetailCommentView(ActivityDetailActivity.this, detailCard.getCommentdata());
                            list.add(baseViewItem);
                        }

                    }

                    MineSpaceView mineSpaceView = new MineSpaceView();
                    list.add(mineSpaceView);
                }
            }

            if (dataUnit.getMinecards() != null) {
                for (MineCard mineCard : dataUnit.getMinecards()) {
                    if (mineCard.getLogincard() != null) {
                        LoginCardView loginCardView = new LoginCardView();
                        list.add(loginCardView);
                        MineSpaceView mineSpaceView = new MineSpaceView();
                        list.add(mineSpaceView);
                    }
                    List<MineCardUnit> cardUnits = mineCard.getCardUnits();
                    if (cardUnits != null) {
                        for (MineCardUnit mineCardUnit : cardUnits) {
                            List<MineItemData> items = mineCardUnit.getItems();
                            if (items != null) {
                                for (int i = 0; i < items.size(); i++) {
                                    MineItemView mineItemView = new MineItemView(getActivity(), items.get(i));
                                    list.add(mineItemView);
                                    if (i < items.size() - 1) {
                                        MineLineView mineLineView = new MineLineView();
                                        list.add(mineLineView);
                                    }
                                    if (i == items.size() - 1) {
                                        MineSpaceView mineSpaceView = new MineSpaceView();
                                        list.add(mineSpaceView);
                                    }
                                }

                            }
                        }

                    }

                }
            }
            if (dataUnit.getCards() != null) {
                for (Card card : dataUnit.getCards()) {
                    List<BannerModel> banners = card.getBanners();
                    if (banners != null && banners.size() > 0) {
                        BannerView bannerView = new BannerView(banners);
                        list.add(bannerView);


                    }
                    List<ColumnData> columnitems = card.getColumnitems();
                    if (columnitems != null && columnitems.size() > 0) {
                        ColumnView columnView = new ColumnView(columnitems);
                        list.add(columnView);

                    }
                    List<CardUnit> cardUnits = card.getCardUnits();
                    if (cardUnits != null) {
                        for (CardUnit cardUnit : cardUnits) {
                            List<ItemData> itemDatas = cardUnit.getItems();
                            if (itemDatas == null || itemDatas.size() == 0) {
                                continue;
                            }
                            if (itemDatas.size() == 1) {
                                ItemView baseViewItem = new ItemView(getActivity(), itemDatas.get(0));
                                list.add(baseViewItem);
                                continue;
                            }
                            ChangedItemView baseViewItem = new ChangedItemView(getActivity(), itemDatas);
                            list.add(baseViewItem);

                        }
                    }
                    List<ItemData> itemDatas = card.getItems();
                    if (itemDatas != null) {
                        for (ItemData itemData : itemDatas) {
                            DoubleItemView mainItemView = new DoubleItemView(getActivity());
                            mainItemView.setItemData(itemData);
                            list.add(mainItemView);
                        }
                    }
                    MineSpaceView mineSpaceView = new MineSpaceView();
                    list.add(mineSpaceView);
                }
            }
            List<ItemData> itemDatas = dataUnit.getItems();
            if (itemDatas != null) {
                for (ItemData itemData : dataUnit.getItems()) {
                    if (itemData.getType() == 1) {//推荐
                        PushItemView mainItemView = new PushItemView(getActivity(), itemData);
                        list.add(mainItemView);
                        LineView lineView = new LineView();
                        list.add(lineView);
                    } else {

                    }

                }
            }
            adapter.notifyDataSetChanged();
        } else {
            loadEmptyViewControl.loadError(event.getData().toString());
        }
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
                Dialog dialog = new Dialog(this, R.style.ThemeDialog);
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null, false);
                Display display = ActivityDetailActivity.this.getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
                dialog.setContentView(view, layoutParams);
                GridView grid = (GridView) view.findViewById(R.id.grid);
                List<ShareItem> list = new ArrayList<ShareItem>();
                list.add(create("微信好友", R.drawable.share_ic_base_share_weixin));
                list.add(create("朋友圈", R.drawable.share_ic_base_share_weixin_friends));
                list.add(create("QQ", R.drawable.share_ic_base_share_qq));
                list.add(create("Qzone", R.drawable.share_ic_base_share_qzone));
                ListGridAdapter adapter = new ListGridAdapter(list);
                grid.setNumColumns(list.size() > 4 ? 4 : list.size());
                grid.setAdapter(adapter);
                dialog.show();
                break;
            }
            case R.id.favorite: {

                break;
            }
            case R.id.more: {
                break;
            }
        }
    }


    private static final int THUMB_SIZE = 150;

    private class ListGridAdapter extends BaseAdapter {
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
                            WXTextObject textObj = new WXTextObject();
                            textObj.text = "";
                            if (TextUtils.isEmpty(textObj.text)) {
                                ToastUtils.showToast("没填写");
                                return;
                            }
                            WXMediaMessage msg = new WXMediaMessage();
                            msg.mediaObject = textObj;
                            msg.description = textObj.text;
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("text"); //
                            req.message = msg;
                            req.scene = shareItem.getImgid() == R.drawable.share_ic_base_share_weixin ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                            iwxapi.sendReq(req);

                        } else {
                            WXWebpageObject webpage = new WXWebpageObject();
                            webpage.webpageUrl = "";
                            WXMediaMessage msg = new WXMediaMessage(webpage);
                            msg.title = "";
                            msg.description = "";
                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                            bmp.recycle();
                            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("webpage");
                            req.message = msg;
                            req.scene = shareItem.getImgid() == R.drawable.share_ic_base_share_weixin ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                            iwxapi.sendReq(req);

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
