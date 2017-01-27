package com.cliffesto.cliffesto.activities;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.adapters.ScheduleAdapter;
import com.cliffesto.cliffesto.beans.ScheduleBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultsAndMoreActivity extends AppCompatActivity {

    public Typeface tf;
    ProgressBar progressBar, progressBar2, progressBar3;
    TextView title;
    DatabaseReference myRef;
    private RecyclerView mRecyclerView, recyclerView2, recyclerView3;
    private List<ScheduleBean> mUsers = new ArrayList<>();
    private List<ScheduleBean> mUsers2 = new ArrayList<>();
    private List<ScheduleBean> mUsers3 = new ArrayList<>();
    private ScheduleAdapter mUserAdapter, adapter2, adapter3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_and_more);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cliffesto");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
        mRecyclerView.setLayoutManager(layoutManager);
        mUserAdapter = new ScheduleAdapter(mUsers, this);
        adapter2 = new ScheduleAdapter(mUsers2, this);
        adapter3 = new ScheduleAdapter(mUsers3, this);
        mRecyclerView.setAdapter(mUserAdapter);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setAdapter(adapter3);
        recyclerView2.setAdapter(adapter2);
        new MyTask().execute();
        new MyTask2().execute();
        new MyTask3().execute();
    }

    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("schedule").child("17").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            ScheduleBean stu = new ScheduleBean(hashMap1.get("url").toString(),
                                    hashMap1.get("title").toString(),
                                    hashMap1.get("time").toString());
                            mUsers.add(stu);
                        }
                        mUserAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(ResultsAndMoreActivity.this, "comming soon!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return null;
        }
    }

    private class MyTask2 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("schedule").child("18").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers2.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            ScheduleBean stu = new ScheduleBean(hashMap1.get("url").toString(),
                                    hashMap1.get("title").toString(),
                                    hashMap1.get("time").toString());
                            mUsers2.add(stu);
                        }
                        adapter2.notifyDataSetChanged();
                        progressBar2.setVisibility(View.INVISIBLE);
                    } else {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return null;
        }
    }

    private class MyTask3 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("schedule").child("19").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers3.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            ScheduleBean stu = new ScheduleBean(hashMap1.get("url").toString(),
                                    hashMap1.get("title").toString(),
                                    hashMap1.get("time").toString());
                            mUsers3.add(stu);
                        }
                        adapter3.notifyDataSetChanged();
                        progressBar3.setVisibility(View.INVISIBLE);
                    } else {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return null;
        }
    }
}
