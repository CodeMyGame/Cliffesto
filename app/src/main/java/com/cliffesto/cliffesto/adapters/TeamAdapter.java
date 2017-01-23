package com.cliffesto.cliffesto.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.TeamBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;
import com.cliffesto.cliffesto.picaso.PicasoClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {
    int previousPosition = 0;
    Context context;
    boolean isClick = false;
    private List<TeamBean> gallaryList;
    private DatabaseReference mDatabase;

    public TeamAdapter(List<TeamBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public TeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teams_row, parent, false);
        return new TeamAdapter.MyViewHolder(itemView);
    }

    public void call(String no) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + no));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(callIntent);
        } else {
            Toast.makeText(context, "can't make call", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBindViewHolder(TeamAdapter.MyViewHolder holder, final int position) {
        TeamBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        holder.tvDescription.setText(gallary.getDescription());
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
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("cliffesto").child("teams").child("" + position).child("mobile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String no = dataSnapshot.getValue().toString();
                        if (isClick) {
                            call(no);
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
        public TextView tvDescription;
        public ImageView imageView, call;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
            tvDescription = (TextView) itemView.findViewById(R.id.mobile);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            call = (ImageView) itemView.findViewById(R.id.call);
            //batch = (Button)itemView.findViewById(R.id.btn_batch);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
