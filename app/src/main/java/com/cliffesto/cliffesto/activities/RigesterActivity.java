package com.cliffesto.cliffesto.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cliffesto.cliffesto.R;
import com.cliffesto.cliffesto.beans.RegisterBean;
import com.cliffesto.cliffesto.emailsender.GmailSender;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A login screen that offers login via email/password.
 */
public class RigesterActivity extends AppCompatActivity {

    public static final String EMAIL = "cliffesto.app@gmail.com";
    public static final String PASSWORD = "cliffestoapp";
    String cliffid = "1000";
    EditText name;
    EditText email;
    EditText phone;
    EditText college;
    Button register;
    String getname;
    String getemail;
    String getmobile;
    String getcollege;
    boolean isClick;
    TextView cliffidText;
    File dir;
    View view;
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    public void viewPDF() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cliffesto/" + "cliffesto2017AppID.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 150
                    , 150, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 150, 0, 0, w, h);
        return bitmap;
    }

    public void createandDisplayPdf(String date, String text, String cliffid) {

        Document doc = new Document(PageSize.A4, 0f, 0f, 0f, 0f);

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cliffesto";
            dir = new File(path);

            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, "cliffesto2017AppID.pdf");
            if (file.exists())
                file.delete();
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);
            doc.open();
            InputStream ims = getAssets().open("logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(70f, 70f);
            image.setAbsolutePosition(480f, 750f);
            Bitmap barcode = encodeAsBitmap(cliffid);
            ByteArrayOutputStream streamBar = new ByteArrayOutputStream();
            barcode.compress(Bitmap.CompressFormat.PNG, 10, streamBar);
            Image imageBar = Image.getInstance(streamBar.toByteArray());
            imageBar.scaleToFit(70f, 70f);
            imageBar.setAbsolutePosition(60f, 740f);
            doc.add(image);
            doc.add(imageBar);
            float lineSpacing;
            lineSpacing = 10f;
            Chunk chunk = new Chunk(
                    "Cliffesto 2017 app registration");
            Paragraph p3 = new Paragraph(chunk);
            Paragraph p0 = new Paragraph(new Phrase(lineSpacing, text,
                    FontFactory.getFont(FontFactory.HELVETICA, 14f)));
            Paragraph p1 = new Paragraph(new Phrase(0f, date,
                    FontFactory.getFont(FontFactory.HELVETICA, 15f)));
            Font paraFont = new Font(Font.FontFamily.HELVETICA, 50f);
            Paragraph p2 = new Paragraph(new Phrase(50f, "Cliffesto 2017",
                    FontFactory.getFont(FontFactory.HELVETICA, 25f)));
            Paragraph p4 = new Paragraph(new Phrase(72f, "APPCLIFF" + cliffid,
                    FontFactory.getFont(FontFactory.HELVETICA, 15f)));
            p4.setAlignment(Element.ALIGN_LEFT);
            p1.setAlignment(Element.ALIGN_RIGHT);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p0.setAlignment(Element.ALIGN_LEFT);
            p2.setFont(paraFont);
            p4.setIndentationLeft(20);
            p3.setIndentationLeft(10);
            p1.setIndentationRight(20);
            p0.setIndentationLeft(20);
            p1.setSpacingAfter(70);
            doc.add(p3);
            doc.add(p2);
            doc.add(p4);
            doc.add(p1);
            doc.add(p0);
        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }
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
        createandDisplayPdf("hjhgj", "hjvgjhg", "hbjhgjh");
        viewPDF();
        progressDialog = new ProgressDialog(RigesterActivity.this);
        progressDialog.setMessage("Registring.....");
        progressDialog.setCancelable(false);
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
                    view = v;
                    if (validation()) {
                        isClick =true;
                        register.setText("please wait....");
                        register.setEnabled(false);
                        RegisterBean registerBean = new RegisterBean(
                                getname,getemail,getmobile,getcollege);
                        mDatabase.child("cliffesto").child("users").child(getmobile).setValue(registerBean);
                        mDatabase.child("cliffesto").child("cliffid").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(isClick){
                                    cliffid = dataSnapshot.getValue().toString();
                                    isClick = false;
                                    mDatabase.child("cliffesto").child("cliffid").setValue(Integer.parseInt(cliffid)+1);
                                    new MyTask().execute();
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

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                GmailSender sender = new GmailSender(EMAIL, PASSWORD);
                sender.sendMail("CLIFFESTO 2k17 APP ID",
                        "Hii " + getname + " you are successfully registered now you can use your id (APPCLIFF" + cliffid + ") to participate in any event",
                        EMAIL,
                        getemail);
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                createandDisplayPdf("Date : " + day + "/" + month + "/" + year, "content.....", cliffid);
            } catch (Exception e) {
                Toast.makeText(RigesterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            cliffidText.setText("Your cliffesto app ID and attachment sent to your email address please download this attachment");
            // Toast.makeText(RigesterActivity.this, "Successfully registered!!!", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "Successfully registered!!!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            name.setText("");
            email.setText("");
            phone.setText("");
            college.setText("");
            register.setEnabled(true);
            register.setText("register");
        }
    }
}

