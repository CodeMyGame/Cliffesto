package com.cliffesto.cliffesto.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.RegisterBean;
import com.cliffesto.cliffesto.emailsender.GmailSender;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static android.Manifest.permission.READ_CONTACTS;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * A login screen that offers login via email/password.
 */
public class RigesterActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;
    EditText college;
    Button register;
    String getname;
    String getemail;
    String getmobile;
    String getcollege;
    private DatabaseReference mDatabase;
    boolean isClick;
    TextView cliffidText;
    public static final String EMAIL ="kapil.nituk@gmail.com";
    public static final String PASSWORD ="ieirodov";
    public boolean validation() {
        boolean valid = true;
        getname = name.getText().toString().trim();
        getemail = email.getText().toString().trim();
        getmobile = phone.getText().toString();
        getcollege = college.getText().toString();
        if (getemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getemail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (getcollege.isEmpty() || college.length() < 10 || college.length() > 30) {
            college.setError("between 10 and 30 alphanumeric characters");
            valid = false;
        } else {
            college.setError(null);
        }
        if (getname.isEmpty()) {
            name.setError("Enter your name");
            valid = false;
        } else {
            name.setError(null);
        }
        if (getmobile.isEmpty() || phone.length() > 12 || phone.length() < 10) {
            phone.setError("Enter valid mobile number");
            valid = false;
        } else {
            phone.setError(null);
        }
        return valid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);
        register = (Button) findViewById(R.id.email_sign_in_button);
        cliffidText = (TextView)findViewById(R.id.textViewCliffid);
        isClick = false;
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (validation()) {
                        isClick =true;
                        RegisterBean registerBean = new RegisterBean(
                                getname,getemail,getmobile,getcollege);
                        mDatabase.child("cliffesto").child("users").child(getmobile).setValue(registerBean);

                        Toast.makeText(RigesterActivity.this, "Register successfully!!!", Toast.LENGTH_SHORT).show();
                        mDatabase.child("cliffesto").child("cliffid").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(isClick){
                                    String cliffid = dataSnapshot.getValue().toString();
                                    isClick = false;
                                    cliffidText.setText("APPCLIFF2017"+cliffid);
                                    mDatabase.child("cliffesto").child("cliffid").setValue(Integer.parseInt(cliffid)+1);
                                    try {
                                        GmailSender sender = new GmailSender("kapil.nituk@gmail.com", "ieirodov");
                                        sender.sendMail("This is Subject",
                                                "This is Body",
                                                "kapil.nituk@gmail.com",
                                                "kapil.gehlot1995@gmail.com");
                                        Toast.makeText(RigesterActivity.this, "Success!!!", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(RigesterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                } catch (Exception e) {
                    Toast.makeText(RigesterActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

