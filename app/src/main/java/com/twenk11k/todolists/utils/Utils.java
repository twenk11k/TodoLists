package com.twenk11k.todolists.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;

public class Utils {

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public static void initUtils(Context context) {
        settings = context.getSharedPreferences("VALUES", Context.MODE_PRIVATE);
    }

    public static boolean getIsAutoEnabled() {
        return settings.getBoolean("auto_enabled", false);
    }

    public static void setIsAutoEnabled(boolean val) {
        editor = settings.edit();
        editor.putBoolean("auto_enabled",val);
        editor.apply();
    }

    public static void setPermissionExternalEnabled(boolean val) {
        editor = settings.edit();
        editor.putBoolean("permission_external_enabled",val);
        editor.apply();
    }

    public static boolean getPermissionExternalEnabled() {
        return settings.getBoolean("permission_external_enabled", false);
    }


    public static int getStatHeight(Context context) {
        int height = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = context.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    public static String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(c);
    }


    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9F);
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0D,to = 2.0D) float by) {
        if (by == 1.0F) {
            return color;
        } else {
            int alpha = Color.alpha(color);
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            hsv[2] *= by;
            return (alpha << 24) + (16777215 & Color.HSVToColor(hsv));
        }
    }

}
