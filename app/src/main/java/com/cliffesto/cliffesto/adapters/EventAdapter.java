package com.cliffesto.cliffesto.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.activities.EnentInfoActivity;
import com.cliffesto.cliffesto.activities.EventRegister;
import com.cliffesto.cliffesto.activities.MainActivity;
import com.cliffesto.cliffesto.beans.EventBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;
import com.cliffesto.cliffesto.picaso.PicasoClient;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    int previousPosition = 0;
    Context context;
    private List<EventBean> gallaryList;


    public EventAdapter(List<EventBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new EventAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventAdapter.MyViewHolder holder, final int position) {
        EventBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        //  holder.tvDescription.setText(gallary.getDescription());
        PicasoClient.downLoadImg(context, gallaryList.get(position).url, holder.imageView);
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
        holder.event_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.vibe.vibrate(15);
                Intent intent = new Intent(context, EventRegister.class);
                intent.putExtra("eventname", holder.tvHeading.getText().toString());
                context.startActivity(intent);
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                MainActivity.vibe.vibrate(15);
                Intent intent = new Intent(context, EnentInfoActivity.class);
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
        //  public TextView tvDescripvetion;
        public ImageView imageView;
        ImageView event_register, more;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            event_register = (ImageView) itemView.findViewById(R.id.event_register);
            more = (ImageView) itemView.findViewById(R.id.more);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
