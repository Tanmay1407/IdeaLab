package com.lnct.ac.in.idealab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utils {

    private static Utils util = new Utils();

    public Utils() {

    }

    public static Utils getInstance(Context context) {
//        util.c = context;
        return util;
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isAvailable() && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean hasStoragePermission(Context c) {
        if(ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity)c, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            return false;
        }
        if(ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void requestStoragePermission(Context c) {
        if(ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)c, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
        if(ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)c, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }

}
