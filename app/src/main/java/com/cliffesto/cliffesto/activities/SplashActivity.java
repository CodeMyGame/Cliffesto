package com.cliffesto.cliffesto.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.cliffesto.cliffesto.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/splash.html");
//        Thread timer = new Thread(){
//            @Override
//            public void run() {
//                try{
//                    sleep(4000);
//
//                }catch(InterruptedException e){
//                    getStackTrace();
//                }finally {
//                    Intent openActivity = new Intent(getBaseContext(),MainActivity.class);   //here it should be match with action name of activity which occur after spalsh activity
//                    startActivity(openActivity);
//                }
//            }
//        };
//        timer.start();

    }
}
