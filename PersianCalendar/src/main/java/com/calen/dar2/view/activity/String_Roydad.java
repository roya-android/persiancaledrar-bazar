package com.calen.dar2.view.activity;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class String_Roydad extends SQLiteAssetHelper{

    private final static String DB_NAME = "roydad.db";
    private final static int VERSION = 1;


    public String_Roydad(Context context) {
        super(context, DB_NAME, null, VERSION);

    }


}
