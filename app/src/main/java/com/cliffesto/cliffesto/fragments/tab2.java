package com.cliffesto.cliffesto.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.adapters.HomeAdapter;
import com.cliffesto.cliffesto.adapters.TeamAdapter;
import com.cliffesto.cliffesto.beans.HomeBean;
import com.cliffesto.cliffesto.beans.TeamBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {

    private RecyclerView mRecyclerView;
    private List<TeamBean> mUsers = new ArrayList<>();
    private TeamAdapter mUserAdapter;
    ProgressBar progressBar;
    TextView title;
    DatabaseReference myRef;
    public Typeface tf;
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cliffesto");
        title = (TextView)view.findViewById(R.id.title_cliff);
        tf = Typeface.createFromAsset(getActivity().getAssets(),"2.ttf");
        title.setTypeface(tf);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(glm);
        mUserAdapter = new TeamAdapter(mUsers, getActivity());
        mRecyclerView.setAdapter(mUserAdapter);
        new MyTask().execute();
        return view;


    }


    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("teams").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            TeamBean stu = new TeamBean(hashMap1.get("dp").toString(),
                                    hashMap1.get("name").toString(),
                                    hashMap1.get("mobile").toString());
                            mUsers.add(stu);
                        }
                        mUserAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
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
