package com.example.my.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by 4261305 on 2016/3/1.
 */
public class NewsActivity extends Activity {
    private WebView mWebView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Intent intent = getIntent();
        String url = getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.WebView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);


    }


}
