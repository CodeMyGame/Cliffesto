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

import com.bumptech.glide.Glide;
import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.activities.EventInfoActivity;
import com.cliffesto.cliffesto.activities.EventRegister;
import com.cliffesto.cliffesto.activities.MainActivity;
import com.cliffesto.cliffesto.beans.EventBean;
import com.cliffesto.cliffesto.picaso.AnimationUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Kapil Gehlot on 1/20/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    boolean isClick = false;
    private int previousPosition = 0;
    private Context context;
    private List<EventBean> gallaryList;
    private DatabaseReference mDatabase;
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
                MainActivity.vibe.vibrate(15);
                Intent intent = new Intent(context, EventInfoActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
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
            @Override
            public void onClick(View v) {
                MainActivity.vibe.vibrate(15);
                isClick = true;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("cliffesto").child("eventcall").child("" + position).child("mobile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String no = dataSnapshot.getValue().toString();
                            if (isClick) {
                                call(no);
                                isClick = false;
                            }
                        } else {
                            Toast.makeText(context, "Mobile number not available!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void call(String no) {
        MainActivity.vibe.vibrate(15);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + no));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(callIntent);
        } else {
            Toast.makeText(context, "can't make call", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
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
