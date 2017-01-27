package com.cliffesto.cliffesto.fragments;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.adapters.EventAdapter;
import com.cliffesto.cliffesto.beans.EventBean;
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
public class tab3 extends Fragment {


    public Typeface tf;
    ProgressBar progressBar;
    TextView title;
    DatabaseReference myRef;
    private RecyclerView mRecyclerView;
    private List<EventBean> mUsers = new ArrayList<>();
    private EventAdapter mUserAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        title = (TextView)view.findViewById(R.id.title_cliff);
        tf = Typeface.createFromAsset(getActivity().getAssets(),"2.ttf");
        title.setTypeface(tf);
        myRef = database.getReference("cliffesto");
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),2);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        mUserAdapter = new EventAdapter(mUsers, getActivity());
        mRecyclerView.setAdapter(mUserAdapter);
        new MyTask().execute();
        return view;


    }


    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("events").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            EventBean stu = new EventBean(hashMap1.get("url").toString(),
                                    hashMap1.get("name").toString());
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
