package com.calen.dar2.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.calen.dar2.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

public class imgshow extends Activity {
    Bitmap mIcon_val;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgshow);
        img=(ImageView)findViewById(R.id.imageView1);

        img.setEnabled(false);
        final String strlink;
        final String strimglink;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                strlink= null;
                strimglink= null;
            } else {
                strimglink= extras.getString("strimglink");
                strlink= extras.getString("strlink");

            }
        } else {
            strlink= (String) savedInstanceState.getSerializable("strlink");
            strimglink= (String) savedInstanceState.getSerializable("strimglink");
        }

        new DownloadImage().execute(strimglink);

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
                    if (i>=200) {
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

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                img.setEnabled(false);
                finish();
                openuri(strlink);
            }
        });

    }

    private void openuri(String link)
    {

        String url = ""+link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        getApplication(). startActivity(i);
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


        protected void onPostExecute(Drawable image)
        {

            img.setImageDrawable(image);
        }

        private Drawable downloadImage(String _url)
        {
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
    @Override
    public void onBackPressed() {
    }

}
