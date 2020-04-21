package com.calen.dar2.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.calen.dar2.CalendarTool;
import com.calen.dar2.R;
import com.calen.dar2.view.fragment.CalendarFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Roydad extends Fragment {

    private StringBuffer postList;
    CalendarTool calendarTool = new CalendarTool();
    private ProgressDialog progressDialog;
    private TextView txt_roz, txt_addad, txt_hafte, txt_roydad, txtheader;
    private Typeface typeface;
    private Button btn_show;
    private Spinner sp1, sp2;
    private ArrayAdapter<String> arrayAdapter_mah;
    private ArrayAdapter<Integer> arrayAdapter_roz;
    private List<String> list_mah;
    private List<Integer> list_roz;
    private String[] str = {"فروردین", "اردیبهشت", "خرداد",
            "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر",
            "دی", "بهمن", "اسفند"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_roydad, container, false);

        sp1 = (Spinner) view.findViewById(R.id.sp1);
        sp2 = (Spinner) view.findViewById(R.id.sp2);


        btn_show = (Button) view.findViewById(R.id.btn_show);

//        list_mah = new ArrayList<>();
        list_roz = new ArrayList<>();


        for (int i = 1; i <= 31; i++) {
            list_roz.add(i);
        }

        list_mah = Arrays.asList(str);

        txt_addad = (TextView) view.findViewById(R.id.txtaddad);
        txt_hafte = (TextView) view.findViewById(R.id.txthafte);
        txt_roz = (TextView) view.findViewById(R.id.txtroz);
        txt_roydad = (TextView) view.findViewById(R.id.txtroydad);
        txtheader = (TextView) view.findViewById(R.id.txtheader);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iranm.ttf");

        txt_roydad.setTypeface(typeface);
        txt_roz.setTypeface(typeface);
        txt_hafte.setTypeface(typeface);
        txt_addad.setTypeface(typeface);
        btn_show.setTypeface(typeface);
        txtheader.setTypeface(typeface);


        txt_roz.setText(calendarTool.getIranianWeekDayStr());
        txt_addad.setText(Top_Form.tp);
        txt_hafte.setText(calendarTool.getIranianMonthStr());
//        txt_roydad.(CalendarFragment.roydad);

        arrayAdapter_roz = new ArrayAdapter<Integer>
                (getContext(), android.R.layout.simple_list_item_1, list_roz);
        sp1.setAdapter(arrayAdapter_roz);

        arrayAdapter_mah = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, list_mah);
        sp2.setAdapter(arrayAdapter_mah);


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tbname = null;
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                databaseAccess.open();
                if (sp2.getSelectedItem().toString().equals("فروردین")) {
                    tbname = "tbl_fa";
                } else if (sp2.getSelectedItem().toString().equals("اردیبهشت")) {
                    tbname = "tbl_or";
                } else if (sp2.getSelectedItem().toString().equals("خرداد")) {
                    tbname = "tbl_kh";
                } else if (sp2.getSelectedItem().toString().equals("تیر")) {
                    tbname = "tbl_tir";
                } else if (sp2.getSelectedItem().toString().equals("مرداد")) {
                    tbname = "tbl_mor";
                } else if (sp2.getSelectedItem().toString().equals("شهریور")) {
                    tbname = "tbl_sh";
                } else if (sp2.getSelectedItem().toString().equals("مهر")) {
                    tbname = "tbl_mehr";
                } else if (sp2.getSelectedItem().toString().equals("آبان")) {
                    tbname = "tbl_ab";
                } else if (sp2.getSelectedItem().toString().equals("آذر")) {
                    tbname = "tbl_az";
                } else if (sp2.getSelectedItem().toString().equals("دی")) {
                    tbname = "tbl_day";
                } else if (sp2.getSelectedItem().toString().equals("بهمن")) {
                    tbname = "tbl_ba";
                } else if (sp2.getSelectedItem().toString().equals("اسفند")) {
                    tbname = "tbl_es";
                }
                List<String> quotes = databaseAccess.getQuotes(tbname);
                databaseAccess.close();

                txtheader.setText(quotes.get(sp1.getSelectedItemPosition()) + "\n");


            }
        });

        String tbname = null;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();


        switch (calendarTool.getIranianMonth()) {
            case 1:
                tbname = "tbl_fa";
                break;
            case 2:
                tbname = "tbl_or";
                break;
            case 3:
                tbname = "tbl_kh";
                break;
            case 4:
                tbname = "tbl_tir";
                break;
            case 5:
                tbname = "tbl_mor";
                break;
            case 6:
                tbname = "tbl_sh";
                break;
            case 7:
                tbname = "tbl_mehr";
                break;
            case 8:
                tbname = "tbl_ab";
                break;
            case 9:
                tbname = "tbl_az";
                break;
            case 10:
                tbname = "tbl_day";
                break;
            case 11:
                tbname = "tbl_ba";
                break;
            case 12:
                tbname = "tbl_es";
                break;


        }

        List<String> quotes = databaseAccess.getQuotes(tbname);
        databaseAccess.close();

        txt_roydad.setText(quotes.get(Integer.parseInt(CalendarFragment.roz1) - 1));
// در این قسمت اسپینر دکمه روز رو پر میکنیم که وقت ماه را انتخاب کردیم ان نیز تغییر کند
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                int count = 1;
                count += sp2.getSelectedItemPosition();
                list_roz.clear();
                sp1.setAdapter(null);
                switch (count) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        for (int i = 1; i <= 31; i++) {
                            list_roz.add(i);
                        }

                        break;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        for (int i = 1; i <= 30; i++) {
                            list_roz.add(i);
                        }
                        break;
                    case 12:
                        for (int i = 1; i <= 29; i++) {
                            list_roz.add(i);
                        }
                        break;
                }
                arrayAdapter_roz = new ArrayAdapter<Integer>
                        (getContext(), android.R.layout.simple_list_item_1, list_roz);
                sp1.setAdapter(arrayAdapter_roz);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


}
