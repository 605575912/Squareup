package com.squareup.code.upload.samples;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class BizServer {

    /**
     * cos的用户接口
     */
    protected COSClient cos;

    /**
     * cosclient 配置设置; 根据需要设置；
     */
    protected COSClientConfig config;

    /**
     * 设置园区；根据创建的cos空间时选择的园区
     * 华南园区：COSEndPoint.COS_GZ(已上线)
     * 华北园区：COSEndPoint.COS_TJ(已上线)
     * 华东园区：COSEndPoint.COS_SH
     * 此处Demo中选择了 华东园区：COSEndPoint.COS_SH用于测试
     */
    protected COSEndPoint cosEndPoint;

    /**
     * cos的appid
     */
    public static String appid = "1253839308";

    /**
     * appid的一个空间名称
     */
    public static String bucket = "xy";

    /**
     * 上传测试
     */
    protected String srcPath;
    protected boolean isSliceUpload = false;
    protected boolean checkSha = false;

    /**
     * 下载测试
     */
    protected String downloadUrl;
    protected String savePath;

    /**
     * 删除文件测试
     */
    protected String fileId;


    /**
     * 目录前缀测试
     */
    protected String prefix;

    protected static byte[] object = new byte[0];

    public static BizServer instance;
    private List<String> listPath;

    private BizServer(Context context) {
        config = new COSClientConfig();
        cosEndPoint = COSEndPoint.COS_GZ;
        config.setEndPoint(cosEndPoint);
        cos = new COSClient(context, appid, config, "11");
    }

    public static BizServer getInstance(Context context) {
        if (instance == null) {
            synchronized (object) {
                instance = new BizServer(context);
            }
        }
        return instance;
    }

    public String getBucket() {
        return bucket;
    }

    public COSClient getCOSClient() {
        return cos;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public void setSliceUpload(boolean sliceUpload) {
        this.isSliceUpload = sliceUpload;
    }

    public boolean getSliceUpload() {
        return isSliceUpload;
    }

    public void setCheckSha(boolean checkSha) {
        this.checkSha = checkSha;
    }

    public boolean getCheckSha() {
        return checkSha;
    }

    public List<String> getListPath() {
        return listPath;
    }

    public void setListPath(List<String> listPath) {
        this.listPath = listPath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    /**
     * 对fileID进行URLEncoder编码
     */
    private String urlEncoder(String fileID) {
        if (fileID == null) {
            return fileID;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] strFiled = fileID.trim().split("/");
        int length = strFiled.length;
        for (int i = 0; i < length; i++) {
            if ("".equals(strFiled[i])) continue;
            stringBuilder.append("/");
            try {
                String str = URLEncoder.encode(strFiled[i], "utf-8").replace("+", "%20");
                stringBuilder.append(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fileID.endsWith("/")) stringBuilder.append("/");
        fileID = stringBuilder.toString();
        return fileID;
    }

    /**
     * 获取单次签名
     *
     * @return
     */
    public String getOnceSign() {
        urlEncoder(fileId);
        String onceSign = null;
        String cgi = "http://203.195.194.28/cosv4/getsignv4.php?" + "bucket=" + bucket + "&service=cos&expired=0&path=" + fileId;
        try {
            URL url = new URL(cgi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = bufferedReader.readLine();
            if (line == null) return null;
            JSONObject json = new JSONObject(line);
            if (json.has("sign")) {
                onceSign = json.getString("sign");
            }
            Log.w("XIAO", "onceSign =" + onceSign);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return onceSign;
    }


    /**
     * 获取 hmacSha1
     *
     * @param base
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public byte[] hmacSha1(String base, String key) {
        if (TextUtils.isEmpty(base) || TextUtils.isEmpty(key)) {
            return null;
        }
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = null;
        try {
            mac = Mac.getInstance(type);
            mac.init(secret);
            byte[] digest = mac.doFinal(base.getBytes());
            return digest;
//            return Base64.encodeToString(digest, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

    String sign;

    /**
     * 获取多次签名
     *
     * @return
     */
    public String getSign() {
        if (!TextUtils.isEmpty(sign)) {
            return sign;
        }
        String original = "a=" + appid + "&b=" + bucket + "&k=AKIDxjHaeTmOTJuFJ8A8F6h6GBrITP7ZvkSv&e=" + (System.currentTimeMillis() / 1000 + 3600) + "&t=" + System.currentTimeMillis() / 1000 + "&r=" + 1999 + "&f=";
        byte[] signTmp = hmacSha1(original, "McgV4SfcLp31iQt75qMdiaDbkeJiqoX1");
        byte[] o = original.getBytes();
        byte[] sign = new byte[signTmp.length + o.length];
        for (int i = 0; i < sign.length; i++) {
            if (i < signTmp.length) {
                sign[i] = signTmp[i];
            } else {
                sign[i] = o[i - signTmp.length];
            }
        }
        byte[] e = Base64.encode(sign, Base64.DEFAULT);
        this.sign = new String(e).replaceAll("\n", "");
        return this.sign;
    }

}
