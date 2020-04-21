package com.calen.dar2.privacy_policy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.calen.dar2.R;

public class PrivacyActivity extends AppCompatActivity {


    Toolbar toolbar;
    JustifiedTextView txt_privacy;
    String font = "fonts/iranm.ttf";
    Typeface typeface;
    ScreenSize screenSize;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        toolbar = findViewById(R.id.toolbar_policy);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.dark_current_day1), PorterDuff.Mode.SRC_ATOP);

        typeface = Typeface.createFromAsset(getAssets(), font);
        txt_privacy = findViewById(R.id.txt_privacy);
        screenSize = new ScreenSize(this);
        cardView = findViewById(R.id.card_policy);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            cardView.setBackground(getResources().getDrawable(R.drawable.back_color_policy));
        }
        manageTxt();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    private void manageTxt() {

        int widthScreen = (int) screenSize.getDpwith();
        int i;
        if (widthScreen >= 320 && widthScreen < 405)
            txt_privacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        else if (widthScreen >= 405 && widthScreen < 600)
            txt_privacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        else if (widthScreen >= 600 && widthScreen < 700)
            txt_privacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        else if (widthScreen >= 700)
            txt_privacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        else {
            txt_privacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }
        txt_privacy.setLineSpacing(35);
        txt_privacy.setTextColor(Color.BLACK);
        txt_privacy.setPadding(50, 10, 50, 10);
        txt_privacy.setTypeFace(typeface);
        txt_privacy.setText(getResources().getString(R.string.privacy_policy));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
