package com.fairytale.fortunetarot.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.presenter.BasePresenter;

/**
 * Created by lizhen on 2018/1/30.
 */

public class CardMeaningActivity extends BaseActivity {
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_meaning;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String path = bundle.getString("path");
            String cardName = bundle.getString("cardName","");
            setTv_title(cardName);
            webView.loadUrl(path);
        }
    }

    @Override
    protected void initSelf() {
        needTop(true);
        needBack(true);
        webView = initViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideProgress();
            }
        });
    }
}
