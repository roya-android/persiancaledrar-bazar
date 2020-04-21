package com.calen.dar2.view.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calen.dar2.CalendarTool;
import com.calen.dar2.R;

/**
 * Created by M.R.S.L.Y on 3/23/2018.
 */

public class Top_Form {

    private Activity activity;
    private RelativeLayout relativeLayout;
    private TextView textView1, textView2, textView3, textView4, textView5;
    private int id;
    private String s1, s2, s3, s4, s5,s6;
    private ImageView imageButton;
    private Animation animation;
    private Typeface typeface;
    private DateHigri dateHigri;
    public static String tp;
    private TextView txtroz;
    CalendarTool calendarTool = new CalendarTool();

    public Top_Form(Activity activity, int id,
                    String s1, String s2, String s3, String s4, String s5,String s6) {
        this.activity = activity;
        this.id = id;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        tp = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        init();
    }

    private void init() {
        dateHigri = new DateHigri();
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iranbold.ttf");

        textView1 = (TextView) activity.findViewById(R.id.txtarab);
        textView2 = (TextView) activity.findViewById(R.id.txtmars);
        textView3 = (TextView) activity.findViewById(R.id.txtIran);
        textView4 = (TextView) activity.findViewById(R.id.txtfarvardin);
        textView5 = (TextView) activity.findViewById(R.id.txtsal);
        txtroz = (TextView) activity.findViewById(R.id.txtroz);

//        imageButton = (ImageView)activity.findViewById(R.id.imageView00);
        animation = AnimationUtils.loadAnimation(activity, R.anim.move);
        animation.setRepeatCount(Animation.INFINITE);
//        imageButton.startAnimation(animation);

        Utility utility = new Utility();
        CalendarTool calendarTool = new CalendarTool();

        textView1.setText(s1);
        textView2.setText(s2);
        textView3.setText(s3);
        textView4.setText(s4);
        textView5.setText(s5);

        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
        txtroz.setTypeface(typeface);
//        imageButton.setText(s3);

        txtroz.setText(s6);
    }

    public TextView getTextView1() {
        return textView2;
    }

}
