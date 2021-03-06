package com.cliffesto.cliffesto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.activities.HomeNextActivity;
import com.cliffesto.cliffesto.beans.HomeBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    int previousPosition = 0;
    Context context;
    private List<HomeBean> gallaryList;

    public HomeAdapter(List<HomeBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_row, parent, false);
        return new HomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {
        HomeBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        holder.status.setText(gallary.getStatus());
        Glide
                .with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.img_gallary)
                .crossFade()
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeNextActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
        public ImageView imageView;
        public TextView status;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
            status = (TextView) itemView.findViewById(R.id.status);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
