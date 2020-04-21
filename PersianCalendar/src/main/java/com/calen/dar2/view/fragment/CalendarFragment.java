package com.calen.dar2.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.github.praytimes.Clock;
import com.github.praytimes.Coordinate;
import com.github.praytimes.PrayTime;
import com.github.praytimes.PrayTimesCalculator;
import com.calen.dar2.CalendarTool;
import com.calen.dar2.Constants;
import com.calen.dar2.R;
import com.calen.dar2.TypoGraphy;
import com.calen.dar2.adapter.CalendarAdapter;
import com.calen.dar2.privacy_policy.PrivacyActivity;
import com.calen.dar2.util.Utils;
import com.calen.dar2.view.activity.Top;
import com.calen.dar2.view.activity.Top_Form;
import com.calen.dar2.view.dialog.SelectDayDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;

public class CalendarFragment extends Fragment
        implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager monthViewPager;
    private Utils utils;
    public StringBuilder stringBuilder1;
    private Calendar calendar = Calendar.getInstance();

    private Coordinate coordinate;

    private PrayTimesCalculator prayTimesCalculator;
    private TextView fajrTextView;
    private TextView dhuhrTextView;
    private TextView asrTextView;
    private TextView maghribTextView;
    private TextView ishaTextView;
    private TextView sunriseTextView;
    private TextView sunsetTextView;
    private TextView midnightTextView;

    private TextView weekDayName;
    private TextView gregorianDate;
    private TextView islamicDate;
    private TextView shamsiDate;
    private TextView eventTitle;
    private TextView holidayTitle;
    private TextView today;
    private AppCompatImageView todayIcon;

    private AppCompatImageView moreOwghat;

    private CardView owghat;
    private CardView event;
    private String day, zkr;
    private RelativeLayout fajrLayout;
    private RelativeLayout sunriseLayout;
    private RelativeLayout dhuhrLayout;
    private RelativeLayout asrLayout;
    private RelativeLayout sunsetLayout;
    private RelativeLayout maghribLayout;
    private RelativeLayout ishaLayout;
    private RelativeLayout midnightLayout;
    public static String roz, roz1, roydad;
    private int viewPagerPosition;
    private RelativeLayout container;
    CalendarTool calendarTool = new CalendarTool();


    View view;
    AdView mAdview;
    InterstitialAd interstitialAd;
    RequestQueue requestQueue;

    /* for test
     * https://www.gkarad.com/testId.json*/
    /*main for bazar
     * */

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        utils = Utils.getInstance(getContext());
        utils.clearYearWarnFlag();
        viewPagerPosition = 0;

        initData();
//        initVolley();
        initAdmob();


        return view;

    }

    private void initData() {

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iranm.ttf");

        fajrLayout = (RelativeLayout) view.findViewById(R.id.fajrLayout);
        sunriseLayout = (RelativeLayout) view.findViewById(R.id.sunriseLayout);
        dhuhrLayout = (RelativeLayout) view.findViewById(R.id.dhuhrLayout);
        asrLayout = (RelativeLayout) view.findViewById(R.id.asrLayout);
        sunsetLayout = (RelativeLayout) view.findViewById(R.id.sunsetLayout);
        maghribLayout = (RelativeLayout) view.findViewById(R.id.maghribLayout);
        ishaLayout = (RelativeLayout) view.findViewById(R.id.ishaLayout);
        midnightLayout = (RelativeLayout) view.findViewById(R.id.midnightLayout);

        gregorianDate = (TextView) view.findViewById(R.id.gregorian_date);
        utils.setFont(gregorianDate);
        islamicDate = (TextView) view.findViewById(R.id.islamic_date);
        utils.setFont(islamicDate);
        shamsiDate = (TextView) view.findViewById(R.id.shamsi_date);
        utils.setFont(shamsiDate);
        weekDayName = (TextView) view.findViewById(R.id.week_day_name);
        utils.setFont(weekDayName);
        today = (TextView) view.findViewById(R.id.today);
        todayIcon = (AppCompatImageView) view.findViewById(R.id.today_icon);

        fajrTextView = (TextView) view.findViewById(R.id.fajr);
        utils.setFont(fajrTextView);
        TextView textView = (TextView) view.findViewById(R.id.fajrText);
        textView.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.fajrText));

        dhuhrTextView = (TextView) view.findViewById(R.id.dhuhr);
        utils.setFont(dhuhrTextView);
        TextView textView7 = (TextView) view.findViewById(R.id.dhuhrText);
        textView7.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.dhuhrText));

        asrTextView = (TextView) view.findViewById(R.id.asr);
        utils.setFont(asrTextView);
        TextView textView6 = (TextView) view.findViewById(R.id.asrText);
        textView6.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.asrText));

        maghribTextView = (TextView) view.findViewById(R.id.maghrib);
        utils.setFont(maghribTextView);
        TextView textView5 = (TextView) view.findViewById(R.id.maghribText);
        textView5.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.maghribText));

        ishaTextView = (TextView) view.findViewById(R.id.isgha);
        utils.setFont(ishaTextView);
        TextView textView4 = (TextView) view.findViewById(R.id.ishaText);
        textView4.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.ishaText));

        TextView textView10 = (TextView) view.findViewById(R.id.shari);
        textView10.setTypeface(typeface);

        sunriseTextView = (TextView) view.findViewById(R.id.sunrise);
        utils.setFont(sunriseTextView);
        TextView textView3 = (TextView) view.findViewById(R.id.sunriseText);
        textView3.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.sunriseText));

        sunsetTextView = (TextView) view.findViewById(R.id.sunset);
        utils.setFont(sunsetTextView);
        TextView textView2 = (TextView) view.findViewById(R.id.sunsetText);
        textView2.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.sunsetText));

        midnightTextView = (TextView) view.findViewById(R.id.midnight);
        utils.setFont(midnightTextView);
        TextView textView1 = (TextView) view.findViewById(R.id.midnightText);
        textView1.setTypeface(typeface);
//        utils.setFontAndShape((TextView) view.findViewById(R.id.midnightText));


        container = view.findViewById(R.id.about_layout);
        if(!getContext().getPackageName().equals("com.calen.dar2")){
            container.setVisibility(View.GONE);
            getActivity().finish();

        }

        moreOwghat = (AppCompatImageView) view.findViewById(R.id.more_owghat);

        eventTitle = (TextView) view.findViewById(R.id.event_title);
        utils.setFont(eventTitle);
        holidayTitle = (TextView) view.findViewById(R.id.holiday_title);
        utils.setFont(holidayTitle);

        owghat = (CardView) view.findViewById(R.id.owghat);
        event = (CardView) view.findViewById(R.id.cardEvent);

        monthViewPager = (ViewPager) view.findViewById(R.id.calendar_pager);

        coordinate = utils.getCoordinate();
        prayTimesCalculator = new PrayTimesCalculator(utils.getCalculationMethod());

        monthViewPager.setAdapter(new CalendarAdapter(getChildFragmentManager()));
        monthViewPager.setCurrentItem(Constants.MONTHS_LIMIT / 2);

        monthViewPager.addOnPageChangeListener(this);

        owghat.setOnClickListener(this);
        today.setOnClickListener(this);
        todayIcon.setOnClickListener(this);
        gregorianDate.setOnClickListener(this);
        islamicDate.setOnClickListener(this);
        shamsiDate.setOnClickListener(this);

        utils.setFontAndShape((TextView) view.findViewById(R.id.event_card_title));
        utils.setFontAndShape((TextView) view.findViewById(R.id.today));
        utils.setFontAndShape((TextView) view.findViewById(R.id.owghat_text));

        String cityName = utils.getCityName(false);
        if (!TextUtils.isEmpty(cityName)) {
            ((TextView) view.findViewById(R.id.owghat_text))
                    .append(" (" + utils.shape(cityName) + ")");
        }

        if (!cityName.equals("") && !cityName.equals(null)) {
            textView10.setText("اوقات شرعی به افق " + cityName);
        }

        // This will immediately be replaced by the same functionality on fragment but is here to
        // make sure enough space is dedicated to actionbar's title and subtitle, kinda hack anyway
        PersianDate today = utils.getToday();
        utils.setActivityTitleAndSubtitle(getActivity(), utils.getMonthName(today),
                utils.formatNumber(today.getYear()));
    }

    private void initAdmob() {
        try{

            if (TypoGraphy.key == 1) {
                MobileAds.initialize(getContext(),TypoGraphy.appId);
                mAdview = new AdView(getContext());
                mAdview.setAdSize(AdSize.BANNER);
                mAdview.setAdUnitId(TypoGraphy.bannerId);
                createViewBanner();
                mAdview.loadAd(new AdRequest.Builder().build());


                interstitialAd = new InterstitialAd(getContext());
                interstitialAd.setAdUnitId(TypoGraphy.interId);
                interstitialAd.loadAd(new AdRequest.Builder().build());
//            interstitialAd.setAdListener(new AdListener(){
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                    startActivity(new Intent(getContext(),PrivacyActivity.class)) ;
//                }
//            });
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                }, 6000);
            }
        }catch (Exception e){

        }
    }

    private void createViewBanner() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mAdview.setLayoutParams(params);


        container.addView(mAdview);
    }


    public void changeMonth(int position) {
        monthViewPager.setCurrentItem(monthViewPager.getCurrentItem() + position, true);
    }

    public void selectDay(PersianDate persianDate) {
        weekDayName.setText(utils.shape(utils.getWeekDayName(persianDate)));
        shamsiDate.setText(utils.shape(utils.dateToString(persianDate)));
        CivilDate civilDate = DateConverter.persianToCivil(persianDate);
        gregorianDate.setText(utils.shape(utils.dateToString(civilDate)));

        islamicDate.setText(utils.shape(utils.dateToString(
                DateConverter.civilToIslamic(civilDate, utils.getIslamicOffset()))));


        switch (Utils.p) {
            case 6:
                zkr = "اَللّهُمَّ صَلِّ عَلی مُحَمَّدٍ وَ آلِ مُحَمَّدٍ وَ عَجِّلْ فَرَجَهُمْ";
                day = "خدایا بر محمد و آل محمد درود فرست و در فرج ایشان تعجیل فرما";
                break;
            case 0:
                zkr = "یا رَبَّ الْعالَمین";
                day = "ای پروردگار جهانیان";
                break;
            case 1:
                zkr = "یا ذَالْجَلالِ وَالْاِکْرام";
                day = "ای صاحب جلال و بزرگواری";
                break;
            case 2:
                zkr = "یا قاضِیَ الْحاجات";
                day = "ای برآورنده حاجت ها";
                break;
            case 3:
                zkr = "یا اَرْحَمَ الرّاحِمین";
                day = "ای مهربان ترین مهربانان";
                break;
            case 4:
                zkr = "یا حَیُّ یا قَیّوم";
                day = "ای زنده ، ای پاینده";
                break;
            case 5:
                zkr = "لا اِلهَ اِلّا اللهُ الْمَلِکُ الْحَقُّ الْمُبین";
                day = "نیست خدایی جز الله فرمانروای حق و آشکار";
                break;

        }


        Top_Form top_form = new Top_Form(

                getActivity(),
                R.layout.top_image_layout,
                utils.shape(utils.dateToString(civilDate)),
                utils.shape(utils.dateToString(DateConverter.civilToIslamic(civilDate, utils.getIslamicOffset()))),
                utils.shape(utils.dateToString1(persianDate)),
                utils.shape(utils.dateToString2(persianDate)),
                utils.shape(utils.dateToString3(persianDate)),
                utils.shape(utils.getWeekDayName(persianDate)));
        //        new Roydad().getTextView1().setText(DateHigri.writeIslamicDate());

        roz = utils.shape(utils.dateToString1(persianDate));
        roz1 = utils.shape(utils.dateToString1(persianDate));

        if (utils.getToday().equals(persianDate)) {
            today.setVisibility(View.GONE);
            todayIcon.setVisibility(View.GONE);
            if (utils.iranTime) {
                weekDayName.setText(weekDayName.getText() +
                        utils.shape(" (" + getString(R.string.iran_time) + ")"));
            }
        } else {
            today.setVisibility(View.VISIBLE);
            todayIcon.setVisibility(View.VISIBLE);
        }

        setOwghat(civilDate);
        showEvent(persianDate);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void addEventOnCalendar(PersianDate persianDate) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);

        CivilDate civil = DateConverter.persianToCivil(persianDate);

        intent.putExtra(CalendarContract.Events.DESCRIPTION,
                utils.dayTitleSummary(persianDate));

        Calendar time = Calendar.getInstance();
        time.set(civil.getYear(), civil.getMonth() - 1, civil.getDayOfMonth());

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                time.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                time.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        startActivity(intent);
    }

    private void showEvent(PersianDate persianDate) {
        String holidays = utils.getEventsTitle(persianDate, true);
        String events = utils.getEventsTitle(persianDate, false);
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(holidays + "\n");
        stringBuilder1.append(events);
        event.setVisibility(View.GONE);
        holidayTitle.setVisibility(View.GONE);
        eventTitle.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(holidays)) {
            holidayTitle.setText(utils.shape(holidays));
            holidayTitle.setVisibility(View.VISIBLE);
            event.setVisibility(View.VISIBLE);
            Top top2 = new Top(getActivity(), zkr, day);
        } else {
            Top top2 = new Top(getActivity(), zkr, day);
        }

        if (!TextUtils.isEmpty(events)) {
            eventTitle.setText(utils.shape(events));
            eventTitle.setVisibility(View.VISIBLE);
            event.setVisibility(View.VISIBLE);
            roydad = utils.shape(stringBuilder1.toString());
            Top top = new Top(getActivity(), zkr, day);
        } else {
            Top top = new Top(getActivity(), zkr, day);
            roydad = utils.shape("در این روز هیچ رویدادی ثبت نشده است");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setOwghat(CivilDate civilDate) {
        if (coordinate == null) {
            return;
        }

        calendar.set(civilDate.getYear(), civilDate.getMonth() - 1, civilDate.getDayOfMonth());
        Date date = calendar.getTime();

        Map<PrayTime, Clock> prayTimes = prayTimesCalculator.calculate(date, coordinate);

        fajrTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.FAJR)));
        sunriseTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.SUNRISE)));
        dhuhrTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.DHUHR)));
        asrTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.ASR)));
        sunsetTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.SUNSET)));
        maghribTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.MAGHRIB)));
        ishaTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.ISHA)));
        midnightTextView.setText(utils.getPersianFormattedClock(prayTimes.get(PrayTime.MIDNIGHT)));


        owghat.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.owghat:
                fajrLayout.setVisibility(View.VISIBLE);
                sunriseLayout.setVisibility(View.VISIBLE);
                dhuhrLayout.setVisibility(View.VISIBLE);
                asrLayout.setVisibility(View.VISIBLE);
                sunsetLayout.setVisibility(View.VISIBLE);
                maghribLayout.setVisibility(View.VISIBLE);
                ishaLayout.setVisibility(View.VISIBLE);
                midnightLayout.setVisibility(View.VISIBLE);

                moreOwghat.setVisibility(View.GONE);
                break;

            case R.id.today:
            case R.id.today_icon:
                bringTodayYearMonth();
                break;

            case R.id.islamic_date:
            case R.id.shamsi_date:
            case R.id.gregorian_date:
                utils.copyToClipboard(v);
                break;
        }
    }

    private void bringTodayYearMonth() {
        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT,
                Constants.BROADCAST_TO_MONTH_FRAGMENT_RESET_DAY);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, -1);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        if (monthViewPager.getCurrentItem() != Constants.MONTHS_LIMIT / 2) {
            monthViewPager.setCurrentItem(Constants.MONTHS_LIMIT / 2);
        }

        selectDay(utils.getToday());
    }

    public void bringDate(PersianDate date) {
        PersianDate today = utils.getToday();
        viewPagerPosition =
                (today.getYear() - date.getYear()) * 12 + today.getMonth() - date.getMonth();

        monthViewPager.setCurrentItem(viewPagerPosition + Constants.MONTHS_LIMIT / 2);

        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT, viewPagerPosition);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, date.getDayOfMonth());

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        selectDay(date);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        viewPagerPosition = position - Constants.MONTHS_LIMIT / 2;

        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT, viewPagerPosition);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, -1);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        today.setVisibility(View.VISIBLE);
        todayIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.action_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to:
                SelectDayDialog dialog = new SelectDayDialog();
                dialog.show(getChildFragmentManager(), SelectDayDialog.class.getName());
                break;
            case R.id.privacy:
                startActivity(new Intent(getContext(), PrivacyActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    public int getViewPagerPosition() {
        return viewPagerPosition;
    }
}
