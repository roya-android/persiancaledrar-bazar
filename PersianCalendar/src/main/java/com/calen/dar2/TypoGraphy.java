package com.calen.dar2;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by saeed on 2/20/17.
 */

public class TypoGraphy extends Application {

    /*ads for test
    * https://www.gkarad.com/testId.json
    * ads main mahmood ghasem
    * https://www.gkarad.com/calendar_bazar_mahmud.json
    * ads main mohsen
    * https://www.gkarad.com/calendar_google_mohsen.json*/


    private String url = "https://www.gkarad.com/calendar_bazar_mahmud.json";
    public static String counter;
    public static String appId,bannerId,interId;
    public static int key;
    @Override
    public void onCreate() {
        super.onCreate();

        //----------------------------
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        key = response.getInt("key");
                        appId = response.getString("appid");
                        bannerId = response.getString("bannerid");
                        interId = response.getString("interid");

                    } catch (JSONException e) {
                        counter = "2";
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    counter = "2";
                }
            });
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        } catch (Exception e) {
            counter = "2";
        }


    }
}
