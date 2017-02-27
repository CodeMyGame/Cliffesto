package com.cliffesto.cliffesto.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventInfoActivity extends AppCompatActivity {

    TextView textView;
    int position;
    boolean isclick = false;
    View v;
    Button register;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enent_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = (TextView) findViewById(R.id.description);
        position = getIntent().getIntExtra("position", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cliffesto").child("events").child("" + position).child("descr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String des = dataSnapshot.getValue().toString();
                    textView.setText(des);
                } else {
                    Toast.makeText(EventInfoActivity.this, "Database empty!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void downloadPDF(View view) {
        v = view;
        isclick = true;
        register = (Button) view.findViewById(R.id.btndownload);
        MainActivity.vibe.vibrate(15);
        mDatabase.child("cliffesto").child("pdf").child("" + position).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    register.setText("Downloading......");
                    register.setEnabled(false);
                    String url = dataSnapshot.getValue().toString();
                    if (isclick) {
                        new DownloadFile().execute(url, "event" + position + ".pdf");
                        isclick = false;
                    }
                } else {
                    Toast.makeText(EventInfoActivity.this, "item not found!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void viewPDF() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "cliffesto");
        File pdfFile = new File(folder, "event" + position + ".pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    class DownloadFile extends AsyncTask<String, String, Void> {
        private static final int MEGABYTE = 1024 * 1024;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EventInfoActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setMessage("Downloading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Snackbar snackbar = Snackbar
                    .make(v, "Download successfull!!!", Snackbar.LENGTH_LONG)
                    .setAction("OPEN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPDF();
                        }
                    });
            Toast.makeText(EventInfoActivity.this, "you can check your downloaded pdf in file manager also!!!!", Toast.LENGTH_SHORT).show();
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
            register.setEnabled(true);
            register.setText("Download PDF");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "cliffesto");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total += bufferLength;
                    fileOutputStream.write(buffer, 0, bufferLength);
                    publishProgress("" + (int) ((total * 100) / totalSize));
                }
                fileOutputStream.close();
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
