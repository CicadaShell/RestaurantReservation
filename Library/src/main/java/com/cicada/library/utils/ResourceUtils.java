package com.cicada.library.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * @Author guocongcong
 * @Date 2018/7/30
 * @Describe 获取资源文件工具类
 */
public class ResourceUtils {

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    public static int getDimen(Context context,int resId) {
        return (int) context.getResources().getDimension(resId);
    }

    public static int getColor(Context context,int resId) {
        return ContextCompat.getColor(context,resId);
    }

    public static String getString(Context context,int resId) {
        return getResources(context).getString(resId);
    }

    public static Drawable getDrawable(Context context,int resId) {
        return ContextCompat.getDrawable(context,resId);
    }

    public static int[] getIntArray(Context context,int resId) {
        return getResources(context).getIntArray(resId);
    }

    public static String[] getStringArray(Context context,int resId) {
        return getResources(context).getStringArray(resId);
    }

}
