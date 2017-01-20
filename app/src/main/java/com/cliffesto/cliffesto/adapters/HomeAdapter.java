package com.cliffesto.cliffesto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.HomeBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;
import com.cliffesto.cliffesto.picaso.PicasoClient;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<HomeBean> gallaryList;
    int previousPosition = 0;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
      //  public TextView tvDescription;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
           // tvDescription = (TextView) itemView.findViewById(R.id.descrp_summer);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            //batch = (Button)itemView.findViewById(R.id.btn_batch);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

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
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, final int position) {
        HomeBean gallary = gallaryList.get(position);
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

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }
}
