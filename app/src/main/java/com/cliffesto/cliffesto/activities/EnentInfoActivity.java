package com.cliffesto.cliffesto.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cliffesto.cliffesto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnentInfoActivity extends AppCompatActivity {

    TextView textView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enent_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.description);
        int position = getIntent().getIntExtra("position", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cliffesto").child("events").child("" + position).child("descr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String des = dataSnapshot.getValue().toString();
                    textView.setText(des);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
