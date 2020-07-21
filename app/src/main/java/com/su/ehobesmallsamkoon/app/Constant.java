package com.su.ehobesmallsamkoon.app;

import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import java.io.File;

public class Constant {
    public static final String TERM_ID = Settings.Secure.getString(App.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
   // public static final String TERM_ID="569918aa5a329ca";//手持机androidId
    public static final String MD5_KEY = "123456";
    public static final String ANDROID_ADDRESS = "1234567890AB";
    public static final String ADDRESS = "Address";
    //数据传递码
    public static int AT_TOP = 0x01, UN_AT_TOP = 0x02;
    public static Uri getFilePath(){
        File file = new File(Environment.getExternalStorageDirectory(), "/EhobeSupplier/pic/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }


}
