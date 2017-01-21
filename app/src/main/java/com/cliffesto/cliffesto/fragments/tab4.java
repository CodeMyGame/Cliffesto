package com.cliffesto.cliffesto.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab4 extends Fragment {


    TextView title;
    DatabaseReference myRef;
    public Typeface tf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        title = (TextView)view.findViewById(R.id.title_cliff);
        tf = Typeface.createFromAsset(getActivity().getAssets(),"2.ttf");
        title.setTypeface(tf);
        return view;

    }

}
