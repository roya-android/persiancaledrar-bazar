package com.calen.dar2.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.pushpole.sdk.PushPoleListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyPushListener extends PushPoleListenerService {
    String link;
    String package_name;
    String btnname;
    String text;
    String imglink;
    String icon;
    String titr;

    @Override
    public void onMessageReceived(JSONObject message, JSONObject content) {
        if (message.length() == 0)
            return; //json is empty
        Log.i("Pushe", "Custom json Message: " + message.toString());



        try {

            int key = message.getInt("key");

            switch (key) {
                case 1:
                    link = message.getString("link");
                    openuri(link);//open uri with browser
                    break;

                case 2:
                    link = message.getString("link");
                    if(appInstalledOrNot("org.telegram.messenger")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.hanista.mobogram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("ir.persianfox.messenger")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("ir.ilmili.telegraph")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("org.ir.talaeii")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.ongram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.mehrdad.blacktelegram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.baranak.turbogramf")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.telegram.hame.mohamad")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("ir.felegram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("ir.rrgc.telegram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("ir.rrgc.telegram")){
                        telegram(link);
                        break;
                    }else if(appInstalledOrNot("com.avina.tg")){
                        telegram(link);
                        break;
                    }else {
                        popup(link);//open telegram link with telegram messenger
                        break;
                    }

                case 3:
                    icon = message.getString("icon");
                    titr = message.getString("titr");
                    text = message.getString("text");
                    link = message.getString("link");
                    btnname = message.getString("btnname");
                    dialog(link, text, btnname, icon, titr);//show dialog page
                    break;

                case 4:
                    imglink = message.getString("imglink");
                    link = message.getString("link");
                    imgshow(imglink, link);//show imgpage
                    break;

                case 5:
                    link=message.getString("link");
                    instagram(link);
                    break;

                case 6:
                    package_name = message.getString("package_name");
                    if (InstalledOrNot(package_name)){
                        Log.e("errorr", "there is this package name");
                    }else {
                        Log.e("errorr", "DownLoading...");
                        link = message.getString("link");
                        InstallApp(link);
                    }
                break;

            }
        } catch (JSONException e) {
            Log.e("","Exception in parsing json" ,e);
        }

    }

    // add by javad
    private void InstallApp(String apkurl) {
        System.out.println("update");
        try {
            URL url = new URL(apkurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH);
            file.mkdirs();

            long filename = System.currentTimeMillis();
            File outputFile = new File(file, filename + ".apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + filename + ".apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Log.e("errorr", "DownLoaded");

        } catch (IOException e) {
            //    Toast.makeText(getApplicationContext(), "InstallApp error!", Toast.LENGTH_LONG).show();
            Log.e("errorr", e.getMessage().toString());
        }
    }

    private void instagram(String link2){

        Uri uri = Uri.parse(link2);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");
        likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    // check the package name is there any package name like this or no?
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void openuri(String link)
    {

        String url = ""+link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        getApplication(). startActivity(i);
    }


    private void popup(String link2){

        String url = "" + link2;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        getApplication().startActivity(i);
    }


    private void dialog(String link, String text, String btnname, String icon, String titr){
        System.out.println("dialog");
        Intent i = new Intent();
        i.setClass(this, Dialog.class);

        i.putExtra("strlink", link);
        i.putExtra("strtitr", titr);
        i.putExtra("stricon", icon);
        i.putExtra("strtext", text);
        i.putExtra("strbtnname", btnname);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }

    private void imgshow(String imglink, String link){
        System.out.println("imgshow");
        Intent i = new Intent();
        i.setClass(this, imgshow.class);

        i.putExtra("strimglink", imglink);
        i.putExtra("strlink", link);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }


    private boolean InstalledOrNot(String package_name){
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(package_name, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void telegram(String link2){

        Uri uri = Uri.parse(link2.toString());
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("org.telegram.messenger");
        if(appInstalledOrNot("org.telegram.messenger")) {

        } else if(appInstalledOrNot("com.hanista.mobogram")) {
            likeIng.setPackage("com.hanista.mobogram");

        } else if(appInstalledOrNot("ir.persianfox.messenger")) {
            likeIng.setPackage("ir.persianfox.messenger");

        } else if(appInstalledOrNot("ir.ilmili.telegraph")) {
            likeIng.setPackage("ir.ilmili.telegraph");

        } else if(appInstalledOrNot("com.mehrdad.blacktelegram")) {
            likeIng.setPackage("com.mehrdad.blacktelegram");

        } else if(appInstalledOrNot("com.baranak.turbogramf")) {
            likeIng.setPackage("com.baranak.turbogramf");

        } else if(appInstalledOrNot("com.telegram.hame.mohamad")) {
            likeIng.setPackage("com.telegram.hame.mohamad");

        } else if(appInstalledOrNot("ir.felegram")) {
            likeIng.setPackage("ir.felegram");

        } else if(appInstalledOrNot("ir.rrgc.telegram")) {
            likeIng.setPackage("ir.rrgc.telegram");

        } else if(appInstalledOrNot("ir.rrgc.telegram")) {
            likeIng.setPackage("ir.rrgc.telegram");

        } else if(appInstalledOrNot("com.avina.tg")) {
            likeIng.setPackage("com.avina.tg");
        }
        likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {

        }
    }
}