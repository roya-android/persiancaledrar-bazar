package com.calen.dar2.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.calen.dar2.R;
//import com.yarolegovich.lovelydialog.LovelyStandardDialog;

/**
 * Created by M.R.S.L.Y on 3/24/2018.
 */

public class Top {

    private String event_str,mani;
    private Activity activity;
    private TextView textView_dialog,txtfarsi;
    private Typeface typeface;
    private Button button,btnExit;
    private Dialog dialog;

    public Top(Activity activity,String event_str,String mani){
        this.activity = activity;
        this.event_str = event_str;
        this.mani = mani;
        init();
    }

    private void init() {

        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iranm.ttf");
        button = (Button)activity.findViewById(R.id.btnto);
//        textView_dialog = (TextView) activity.findViewById(R.id.txtTop);
//        txtfarsi = (TextView) activity.findViewById(R.id.txtf);
//        textView_dialog.setTypeface(typeface);
//        txtfarsi.setTypeface(typeface);
        button.setTypeface(typeface);
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.top_dialog);
        textView_dialog =(TextView) dialog.findViewById(R.id.txt_dialog);
        btnExit = (Button) dialog.findViewById(R.id.btn_dialog_exit);
        txtfarsi = (TextView)dialog.findViewById(R.id.txt_dialog_1);
        textView_dialog.setTypeface(typeface);
        textView_dialog.setText(event_str);
        txtfarsi.setText(mani);
        txtfarsi.setTypeface(typeface);
        btnExit.setTypeface(typeface);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
//        txtfarsi.setText(mani);

    }
}
