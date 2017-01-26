package com.cliffesto.cliffesto.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.fragments.tab1;
import com.cliffesto.cliffesto.fragments.tab2;
import com.cliffesto.cliffesto.fragments.tab3;
import com.cliffesto.cliffesto.fragments.tab4;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    public static Vibrator vibe;
    boolean doubl = false;
    EditText comment;
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
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
                    vibe.vibrate(15);
                    fragment = new tab1();
                }
                else if(tabId==R.id.tab_teams){
                    vibe.vibrate(15);
                    fragment = new tab2();
                }
                else if(tabId==R.id.tab_events) {
                    vibe.vibrate(15);
                    fragment = new tab3();
                }
                else{
                    vibe.vibrate(15);
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
        vibe.vibrate(15);
        startActivity(new Intent(MainActivity.this,RigesterActivity.class));
    }

    public void share(View view) {
        try {
            vibe.vibrate(15);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String body = "Please download Cliffesto app on Google play(https://play.google.com/store/apps/details?id=com.cliffesto.cliffesto)";
            share.putExtra(Intent.EXTRA_SUBJECT, "Cliffesto 2K17");
            share.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(share, "share via"));
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "ERROR:"+e, Toast.LENGTH_SHORT).show();
        }
    }


    public void resultsActivity(View view) {
        vibe.vibrate(15);
        startActivity(new Intent(MainActivity.this, ResultsAndMoreActivity.class));
    }

    public void mapIntent(View view) {
        vibe.vibrate(15);
        double latitude = 30.2195;
        double longitude = 78.7671;
        String label = "NITUK";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void comments(View view) {
        vibe.vibrate(15);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.comment_layout, null);
        ImageView send = (ImageView) v.findViewById(R.id.send);
        comment = (EditText) v.findViewById(R.id.editText);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(15);
                if (comment.getText().length() == 0) {
                    comment.setError("feedback");
                } else {
                    String comments = comment.getText().toString();
                    Toast.makeText(MainActivity.this, "Thank you for your feedback!!!!", Toast.LENGTH_SHORT).show();
                    comment.setText("");

                }
            }
        });
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void chotaRegistration(View view) {
        vibe.vibrate(15);
    }
}
