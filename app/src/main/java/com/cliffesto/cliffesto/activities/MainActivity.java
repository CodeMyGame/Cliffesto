package com.cliffesto.cliffesto.activities;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.fragments.tab1;
import com.cliffesto.cliffesto.fragments.tab2;
import com.cliffesto.cliffesto.fragments.tab3;
import com.cliffesto.cliffesto.fragments.tab4;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
