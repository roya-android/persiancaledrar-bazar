package com.calen.dar2.view.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.calen.dar2.CalendarTool;
import com.calen.dar2.Constants;
import com.calen.dar2.R;
import com.calen.dar2.adapter.DrawerAdapter;
import com.calen.dar2.service.ApplicationService;
import com.calen.dar2.util.UpdateUtils;
import com.calen.dar2.util.Utils;
import com.calen.dar2.view.fragment.ApplicationPreferenceFragment;
import com.calen.dar2.view.fragment.CalendarFragment;
import com.calen.dar2.view.fragment.CompassFragment;
import com.calen.dar2.view.fragment.ConverterFragment;
import com.pushpole.sdk.PushPole;

import me.cheshmak.android.sdk.core.Cheshmak;

/**
 * Program activity for android
 *
 * @author ebraminio
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getName();
    private Utils utils;
    private UpdateUtils updateUtils;
    CalendarTool calendarTool = new CalendarTool();
    private DrawerLayout drawerLayout;
    private DrawerAdapter adapter;

    private Class<?>[] fragments = {
            null,
            CalendarFragment.class,
            ConverterFragment.class,
            CompassFragment.class,
            ApplicationPreferenceFragment.class,
//            Roydad.class
    };

    private static final int CALENDAR = 1;
    private static final int CONVERTER = 2;
    private static final int COMPASS = 3;
    private static final int PREFERENCE = 4;
    private static final int ABOUT = 6;
    private static final int EXIT = 5;
    private FrameLayout n;
    // Default selected fragment
    private static final int DEFAULT = CALENDAR;

    private int menuPosition = 0; // it should be zero otherwise #selectItem won't be called

    private String lastLocale;
    private String lastTheme;
    private int counter = 0, countfasl;

//    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        utils = Utils.getInstance(getApplicationContext());
        utils.setTheme(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        utils.changeAppLanguage(this);
        utils.loadLanguageResource();
        lastLocale = utils.getAppLanguage();
        lastTheme = utils.getTheme();
        updateUtils = UpdateUtils.getInstance(getApplicationContext());

        if (!Utils.getInstance(this).isServiceRunning(ApplicationService.class)) {
            startService(new Intent(getBaseContext(), ApplicationService.class));
        }

        updateUtils.update(true);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);



        // تنظیمات بوت منو
//        bmb = (BoomMenuButton) findViewById(R.id.bmb);
//        assert bmb != null;
//        bmb.setButtonEnum(ButtonEnum.Ham);
//        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
//            bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            toolbar.setPadding(0, 0, 0, 0);
        }


        RecyclerView navigation = (RecyclerView) findViewById(R.id.navigation_view);
        navigation.setHasFixedSize(true);
        adapter = new DrawerAdapter(this);
        navigation.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        navigation.setLayoutManager(layoutManager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        final View appMainView = findViewById(R.id.app_main_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {


            int slidingDirection = +1;

            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (isRTL())
                        slidingDirection = -1;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    slidingAnimation(drawerView, slideOffset);
                }
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private void slidingAnimation(View drawerView, float slideOffset) {
                appMainView.setTranslationX(slideOffset * drawerView.getWidth() * slidingDirection);
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };
//        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
//        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//            }
//        });
        selectItem(DEFAULT);

        LocalBroadcastManager.getInstance(this).registerReceiver(dayPassedReceiver,
                new IntentFilter(Constants.LOCAL_INTENT_DAY_PASSED));
        n = (FrameLayout) findViewById(R.id.fragment_holder);

        setBackgroundFrame();
        /*for push*/
        PushPole.initialize(this,true);


        /*for cheshmack*/
        Cheshmak.with(getApplicationContext());
        Cheshmak.initTracker("kRW1JI7u+aWifVS778pg5Q==");

    }

    private void setBackgroundFrame() {
        switch (calendarTool.getIranianMonth()) {
            case 1:
            case 2:
            case 3:
                n.setBackgroundResource(R.drawable.spring1);
                countfasl = 1;
                break;
            case 4:
            case 5:
            case 6:
                n.setBackgroundResource(R.drawable.summer1);
                countfasl = 2;
                break;
            case 7:
            case 8:
            case 9:
                n.setBackgroundResource(R.drawable.d);
                countfasl = 3;
                break;
            case 10:
            case 11:
            case 12:
                n.setBackgroundResource(R.drawable.winter1);
                countfasl = 4;
                break;


        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isRTL() {
        return getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        utils.changeAppLanguage(this);
        View v = findViewById(R.id.drawer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            v.setLayoutDirection(isRTL() ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }
    }

    public boolean dayIsPassed = false;

    private BroadcastReceiver dayPassedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dayIsPassed = true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (dayIsPassed) {
            dayIsPassed = false;
            restartActivity();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dayPassedReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else if (menuPosition != DEFAULT) {
            selectItem(DEFAULT);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Checking for the "menu" key
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void beforeMenuChange(int position) {
        if (position != menuPosition) {
            // reset app lang on menu changes, ugly hack but it seems is needed
            utils.changeAppLanguage(this);
        }

        // only if we are returning from preferences
        if (menuPosition != PREFERENCE)
            return;

        utils.updateStoredPreference();
        updateUtils.update(true);

        boolean needsActivityRestart = false;

        String locale = utils.getAppLanguage();
        if (!locale.equals(lastLocale)) {
            lastLocale = locale;
            utils.changeAppLanguage(this);
            utils.loadLanguageResource();
            needsActivityRestart = true;
        }

        if (!lastTheme.equals(utils.getTheme())) {
            needsActivityRestart = true;
            lastTheme = utils.getTheme();
        }

        if (needsActivityRestart)
            restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void selectItem(int item) {
        if (item == EXIT) {
            finish();
            return;
        }

//        if(item == ABOUT){
//
//            Intent intent = new Intent(Intent.ACTION_EDIT);
//            intent.setData(Uri.parse("bazaar://details?id=" + "com.gk1.arad1"));
//            intent.setPackage("com.farsitel.bazaar");
//            startActivity(intent);
//
//        }

        beforeMenuChange(item);
        if (menuPosition != item) {
            switch (item) {
                case 1:
                    if (counter != 0) {
                        counter = 0;
                        setBackgroundFrame();
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    counter = 10;
//                    n.setBackgroundColor(Color.rgb(242, 247, 250));break;
                    switch (countfasl) {
                        case 1:
                             n.setBackgroundColor(Color.rgb(217, 239, 238));break;
                        case 2:
                            n.setBackgroundColor(Color.rgb(245, 245, 193));break;
                        case 3:
                            n.setBackgroundColor(Color.rgb(255, 232, 214));break;
                        case 4:
                            n.setBackgroundColor(Color.rgb(242, 247, 250));break;
                    }
                    break;
            }
            try {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.fragment_holder,
                                (Fragment) fragments[item].newInstance(),
                                fragments[item].getName()
                        ).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuPosition = item;
        }

        adapter.setSelectedItem(item);

        drawerLayout.closeDrawers();
    }
}
