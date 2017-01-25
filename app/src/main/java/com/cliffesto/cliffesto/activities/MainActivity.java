package com.cliffesto.cliffesto.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.fragments.tab1;
import com.cliffesto.cliffesto.fragments.tab2;
import com.cliffesto.cliffesto.fragments.tab3;
import com.cliffesto.cliffesto.fragments.tab4;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    boolean doubl = false;
    @Override
    public void onBackPressed() {
        if (doubl) {
            super.onBackPressed();
            finish();
        }
        this.doubl = true;
        Toast.makeText(MainActivity.this, "Please click back again to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubl = false;
            }
        }, 2000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatus));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1
            );
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
            );
        }
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment ;
                if (tabId == R.id.tab_home) {
                    fragment = new tab1();
                }
                else if(tabId==R.id.tab_teams){
                    fragment = new tab2();
                }
                else if(tabId==R.id.tab_events) {
                    fragment = new tab3();
                }
                else{
                    fragment = new tab4();
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.scrollingContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void regiserActivity(View view) {
        startActivity(new Intent(MainActivity.this,RigesterActivity.class));
    }

    public void share(View view) {
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String body = "Please download Cliffesto app on Google play";
            share.putExtra(Intent.EXTRA_SUBJECT, "Cliffesto 2K17");
            share.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(share, "share via"));
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "ERROR:"+e, Toast.LENGTH_SHORT).show();
        }
    }


    public void resultsActivity(View view) {
        startActivity(new Intent(MainActivity.this, ResultsAndMoreActivity.class));
    }
}
