package com.squareup.code.wx;


/**
 * Created by Administrator on 2017/04/18 0018.
 */

public class WxpayModel {

    /**
     * appid : wx4d36b119ac97c60c
     * partnerid : 1382007002
     * prepayid : wx20170418194541bf5a05fb810783034667
     * package : Sign=WXPay
     * noncestr : eaowqo7ttwoxi82080np8cfau2ufwyri
     * timestamp : 1492515941
     * sign : 40AE0192D5F9A87FFB30DF10975C1E8B
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }


    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
