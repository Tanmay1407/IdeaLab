package com.lnct.ac.in.idealab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lnct.ac.in.idealab.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Utils {

    private static Utils util = new Utils();

    public Utils() {

    }

    public static Utils getInstance() {
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

    public static File getDataDir(Context c) {
        return c.getDataDir();
    }

    public static SharedPreferences getPrefs(Context c) {
        return c.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getPrefsEditor(Context c) {
        return c.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
    }

    public static void createDataFile(Context c) {
        File root = getDataDir(c);
    }

    public static void createImageCacheDir(Context c) throws IOException {
        File f = new File(c.getCacheDir(), File.separator + "event_image");
        Log.i("image cache file----", f.getAbsolutePath());
        if(!f.exists()) {
            f.mkdir();
        }
    }

    public static File getImageCacheDir(Context c) {
        return new File(c.getCacheDir()+File.separator+"event_image");
    }

    public static void saveUser(Context context , User u){
        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("USER",u.toString());
        editor.putString("USER_ID", u.get_id());
        editor.commit();

    }

    public static User convertToUserObj(JSONObject userCreated) throws JSONException {
          if ( userCreated.isNull("name")) return null;
        return new User(
                userCreated.get("name").toString(),
                userCreated.get("email").toString(),
                userCreated.get("branch").toString(),
                userCreated.get("college").toString(),
                userCreated.get("phone").toString(),
                userCreated.get("_id").toString()
                );

    }

    public static boolean isUserPresent(Context c){
        if(getPrefs(c).contains("USER")) return true;
        else return false;
    }

}
