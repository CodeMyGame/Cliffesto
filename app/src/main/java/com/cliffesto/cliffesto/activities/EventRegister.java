package com.cliffesto.cliffesto.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.EventregistrationBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventRegister extends AppCompatActivity {

    EditText cliffid;
    EditText eventname;
    Button regButton;
    String event_name;
    int getid;
    boolean isclick = false;
    String getcode;
    boolean isInt;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        event_name = getIntent().getStringExtra("eventname");
        cliffid = (EditText) findViewById(R.id.cliffid);

        regButton = (Button) findViewById(R.id.email_sign_in_button);
        eventname = (EditText) findViewById(R.id.eventname);
        eventname.setEnabled(false);
        eventname.setText(event_name);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                MainActivity.vibe.vibrate(15);
                isclick = true;
                StringBuilder stringBuilder = new StringBuilder(cliffid.getText().toString());
                if (stringBuilder.length() == 12) {

                    getcode = stringBuilder.substring(0, 8);
                    if (!getcode.trim().toLowerCase().equals("appcliff") && isclick) {
                        cliffid.setError("Enter valid cliffesto id");
                        return;
                    }
                    try {
                        getid = Integer.parseInt(stringBuilder.substring(8, stringBuilder.length()));//APPCLIFF1000
                        isInt = true;
                    } catch (NumberFormatException e) {
                        isInt = false;
                        cliffid.setError("Enter valid cliffesto id");
                        return;
                    }

                    mDatabase.child("cliffesto").child("cliffid").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (isclick) {
                                if (getid < 1000 || getid > Integer.parseInt(dataSnapshot.getValue().toString()) && isclick) {
                                    cliffid.setError("Enter valid cliffesto id");
                                    isclick = false;
                                    return;
                                } else {
                                    EventregistrationBean eventregistrationBean = new EventregistrationBean(eventname.getText().toString());
                                    mDatabase.child("cliffesto").child("event_register").child("APPCLIFF" + getid).setValue(eventregistrationBean);
                                    isclick = false;
                                    Snackbar.make(v, "Successfully registered!!!!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    cliffid.setText("");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    cliffid.setError("Enter valid cliffesto id of length 12");
                }
            }
        });


    }
}
