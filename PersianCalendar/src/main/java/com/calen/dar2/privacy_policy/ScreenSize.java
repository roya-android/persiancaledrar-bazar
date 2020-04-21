package com.calen.dar2.privacy_policy;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenSize {

    private float dpwith,dphight;
    private Activity activity;

    public ScreenSize(Activity activity) {
        this.activity = activity;
        /*متغیری برای صفحه نمایش*/
        Display display = activity.getWindowManager().getDefaultDisplay();
        /*برای اندازه گیری صفحه نمایش*/
        DisplayMetrics metrics = new DisplayMetrics();

         display.getMetrics(metrics);

         /*برای یافتن چگالی پیکسل ها در صفحه نمایش ها  از روش زیر استفاده میکنیم*/
        float density = activity.getResources().getDisplayMetrics().density;

        /*برای به دست آوردن عرض گوشی بر اساس dp*/
        dpwith = metrics.widthPixels/density;
        dphight = metrics.heightPixels/density;
    }

    public float getDpwith(){
        return dpwith;
    }
    public float getDphight(){
        return dphight;
    }
}
