package com.cliffesto.cliffesto.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.activities.MainActivity;
import com.cliffesto.cliffesto.beans.SponsersBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/22/2017.
 */

public class SponsersAdapter extends RecyclerView.Adapter<SponsersAdapter.MyViewHolder> {
    int previousPosition = 0;
    Context context;
    boolean isClick = false;
    private List<SponsersBean> gallaryList;
    private DatabaseReference mDatabase;

    public SponsersAdapter(List<SponsersBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public SponsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sponsers_row, parent, false);
        return new SponsersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SponsersAdapter.MyViewHolder holder, final int position) {
        SponsersBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        Glide
                .with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.img_gallary)
                .into(holder.imageView);
        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;
        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                MainActivity.vibe.vibrate(15);
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("cliffesto").child("sponserslink").child("" + position).child("link").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String no = dataSnapshot.getValue().toString();
                        if (isClick) {
                            String url = no;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                            isClick = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
        public ImageView imageView, call;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            call = (ImageView) itemView.findViewById(R.id.call);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

}
