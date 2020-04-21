package com.calen.dar2.view.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.calen.dar2.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;


public class Dialog extends Activity {
    TextView _title, _description, _top;
    Button _btn;

    Bitmap mIcon_val;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


        final String strlink;
        final String strtext;
        final String strbtnname;
        final String strtitr;
        final String stricon;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                strlink = null;
                strtext = null;
                strbtnname = null;
                strtitr = null;
                stricon = null;
            } else {
                strlink = extras.getString("strlink");
                strtext = extras.getString("strtext");
                strbtnname = extras.getString("strbtnname");
                strtitr = extras.getString("strtitr");
                stricon = extras.getString("stricon");
            }
        } else {
            strlink = (String) savedInstanceState.getSerializable("strlink");
            strtext = (String) savedInstanceState.getSerializable("strtext");
            strbtnname = (String) savedInstanceState.getSerializable("strbtnname");
            strtitr = (String) savedInstanceState.getSerializable("strtitr");
            stricon = (String) savedInstanceState.getSerializable("stricon");
        }


        _top = (TextView) findViewById(R.id.tv_dialog_top);
        _title = (TextView) findViewById(R.id.tv_dialog_title);
        _description = (TextView) findViewById(R.id.tv_dialog_description);

        img = (ImageView) findViewById(R.id.im_dialog_logo);
        _btn = (Button) findViewById(R.id.btn_dialog_download);


        ((Button) findViewById(R.id.btn_dialog_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        _title.setText(strtitr);
        _description.setText(strtext);
        _description.setMovementMethod(new ScrollingMovementMethod());

        _btn.setText(strbtnname);


        new DownloadImage().execute(stricon);

        img.setImageBitmap(mIcon_val);

        final int c;
        new Handler().postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {

                img.setEnabled(true);

            }
        }, 3000);



        final Thread th=new Thread(new Runnable() {

            @Override
            public void run() {
                int i=0 ;
                while (i<=200) {
                    if (i>=200)
                    {
                        img.setEnabled(true);
                    }



                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        });


        th.start();


        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strlink));
                    startActivity(myIntent);
                    finish();
                    System.exit(0);


                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {

    }
    private void setImage(Drawable drawable)
    {
        img.setBackgroundDrawable(drawable);
    }
    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... arg0) {

            return downloadImage(arg0[0]);
        }


        protected void onPostExecute(Drawable image) {

            img.setImageDrawable(image);
        }

        private Drawable downloadImage(String _url) {
            //Prepare to download image
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();

                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }
    }


}