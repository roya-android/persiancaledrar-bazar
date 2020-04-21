package com.calen.dar2;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.calen.dar2.service.ApplicationService;
import com.calen.dar2.util.UpdateUtils;
import com.calen.dar2.util.Utils;

public class Widget2x2 extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Utils.getInstance(context).isServiceRunning(ApplicationService.class)) {
            context.startService(new Intent(context, ApplicationService.class));
        }
        UpdateUtils.getInstance(context).update(true);
    }
}
