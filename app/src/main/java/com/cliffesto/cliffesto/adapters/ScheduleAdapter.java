package com.cliffesto.cliffesto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.ScheduleBean;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/24/2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    Context context;
    private List<ScheduleBean> gallaryList;

    public ScheduleAdapter(List<ScheduleBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public ScheduleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shedule_row, parent, false);
        return new ScheduleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ScheduleAdapter.MyViewHolder holder, final int position) {
        ScheduleBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        holder.description.setText(gallary.getTime());
        holder.venue.setText(gallary.getVenue());
        Glide
                .with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.img_gallary)
                .crossFade()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
        public ImageView imageView;
        public TextView description;
        public TextView venue;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.eventname);
            description = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            venue = (TextView) itemView.findViewById(R.id.venue);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
