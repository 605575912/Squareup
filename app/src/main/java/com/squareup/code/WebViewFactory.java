package com.squareup.code;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.lib.activity.SimpleFactroy;

/**
 * @author Jeaman
 *         通用网页
 */
public class WebViewFactory extends SimpleFactroy {
    protected final static String EXTRA_NAME_TITLE = "title";
    public final static String EXTRA_NAME_URL = "url";

    protected String mTitle;
    protected String mUrl;
    protected WebView mWebView;
    protected ProgressBar mProgressBar;
    private WebChromeClientX mChromeCline;

    //progress会start和finish两次，第二次才是正真的进度条
    private boolean mProgressFinishOnce = false;

    public WebViewFactory(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webview_wv_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.webview_pb_progress);
        init();
    }

    protected void init() {
        mTitle = getIntent().getStringExtra(EXTRA_NAME_TITLE);
        if (mUrl == null) {
            mUrl = getIntent().getStringExtra(EXTRA_NAME_URL);
        }
        initWebView();
        loadUrl(mUrl);
    }

    public void retryRequest() {
        mWebView.reload();
    }

    protected void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    String currenturl;

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不需要缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);// 允许js交互
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setUseWideViewPort(true);
        //webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//有些手机可能存在兼容性，页面排版错乱

        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放

        webSettings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!WebViewFactory.this.shouldOverrideUrlLoading(view, url)) {
                    url = url.trim();
                    if (isSchemeUrl(url)) {
                        Uri uri = Uri.parse(url);
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {

                        }
                    } else {
                        //在当前WebView跳转
                        //FIXME:某些链接在goBack时会一直跳转回到最后的连接,暂时用这个判断解决
                        WebHistoryItem item = mWebView.copyBackForwardList().getCurrentItem();
                        if (item == null || !url.equals(item.getOriginalUrl())) {
                            if (url.equals(currenturl) && currenturl != null) {
                                return false;
                            }
                            view.loadUrl(url);
                            currenturl = url;
                        }
                    }
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                //加载错误页面
//                view.loadUrl(UrlPathHelper.getErrorUrl());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(10);
                mProgressFinishOnce = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mProgressFinishOnce) {
                    mProgressBar.setVisibility(View.GONE);
                    mProgressFinishOnce = true;
                }
            }
        });

        mChromeCline = new WebChromeClientX();
        mWebView.setWebChromeClient(mChromeCline);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    private boolean isSchemeUrl(String url) {
        return !(url.startsWith("http://") || url.startsWith("https://"));
    }

    private class WebChromeClientX extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar.getVisibility() == View.VISIBLE) {
                if (newProgress < 11) {
                    mProgressBar.setProgress(10);
                } else {
                    setProgressChangeAnimation(mProgressBar, newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitle == null) {
//                setTitle(title);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            result.cancel();
            return true;
        }

        /**
         * @param view
         * @param url
         * @param message      调用的方法名
         * @param defaultValue 参数（如果是只有一个参数，就直接传字符串""，如果两个或以上就用json：｛"title":"","url":""｝）
         * @param result
         * @return
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            parseMessage(message, defaultValue);
            result.cancel();
            return true;
        }


        private void setProgressChangeAnimation(final ProgressBar progressBar, int toProgress) {
            if (progressBar.getProgress() > toProgress) {
                return;
            }

            final ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), toProgress);
            progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            progressAnimator.setDuration(500);
            if (toProgress == 100) {
                progressAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
//                        progressBar.setAlpha(0);
//                        progressBar.setVisibility(View.GONE);
//                        progressBar.animate().alpha(0).withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressBar.setVisibility(View.GONE);
//                            }
//                        });
                    }
                });
            }
            progressAnimator.start();
        }

    }

    /**
     * 解析网页传过来的数据
     *
     * @param message      调用的方法名
     * @param defaultValue 如果一个参数的话是string类型，两个参数及以上就用json格式
     */
    protected void parseMessage(String message, String defaultValue) {

    }

    private void goBackOrFinish() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBackOrFinish();
    }
}
