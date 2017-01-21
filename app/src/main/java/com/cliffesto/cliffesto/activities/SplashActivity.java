package com.cliffesto.cliffesto.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.cliffesto.cliffesto.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WebView webView = (WebView)findViewById(R.id.webview);
        if(Build.VERSION.SDK_INT>=21)
            webView.loadUrl("file:///android_asset/splash.html");

    }
}
