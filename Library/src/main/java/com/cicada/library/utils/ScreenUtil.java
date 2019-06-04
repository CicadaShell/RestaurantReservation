package com.cicada.library.utils;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @Author guocongcong
 * @Date 2018/8/20
 * @Describe
 */
public class ScreenUtil {

    private static float ORIGINAL_DENSITY = -1;  //原始屏幕密度比率
    public static float ORIGINAL_SCALED_DENSITY = -1;  //原始字体大小比率


    /**
     * ================ 可调参数 start ================
     */
    //绘制页面时参照的设计图尺寸
    private static float DESIGN_WIDTH = 1080f;
    private static float DESIGN_HEIGHT = 1920f;
    private static float DESTGN_INCH = 5.0f;
    //大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度
    private static float BIG_SCREEN_FACTOR = 0.8f;
    /**
     * ================ 可调参数 end ================
     */

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(final Context context) {
        //添加字体变化的监听
        context.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                //字体改变后,将appScaledDensity重新赋值
                if (newConfig != null && newConfig.fontScale > 0) {
                    DisplayMetrics dm = context.getResources().getDisplayMetrics();
                    ORIGINAL_SCALED_DENSITY = dm.scaledDensity;
                    resetDensity(context);
                }
            }

            @Override
            public void onLowMemory() {
            }
        });
    }

    /**
     * 初始化（可选参数，自行确定设计稿宽高比）
     *
     * @param context
     * @param width
     * @param height
     * @param inch
     * @param zoom
     */
    public static void init(final Context context, float width, float height, float inch, float zoom) {
        DESIGN_WIDTH = width;
        DESIGN_HEIGHT = height;
        DESTGN_INCH = inch;
        BIG_SCREEN_FACTOR = zoom;
        init(context);
    }

    /**
     * 重置屏幕密度
     */
    public static void resetDensity(final Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        //保存默认比率
        if (ORIGINAL_DENSITY == -1) {
            ORIGINAL_DENSITY = dm.density;
            ORIGINAL_SCALED_DENSITY = dm.scaledDensity;
        }

        //1.确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //2.确定参照屏幕密度比率
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT)
                / DESTGN_INCH / DisplayMetrics.DENSITY_DEFAULT;
        //3.确定最终屏幕密度比率
        float relativeDensity = referenceDensity * rate;
        if (relativeDensity > ORIGINAL_DENSITY) {
            relativeDensity = ORIGINAL_DENSITY + (relativeDensity - ORIGINAL_DENSITY) * BIG_SCREEN_FACTOR;
        }

        //4.设置系统默认比率
        dm.density = relativeDensity;
        dm.densityDpi = (int) (relativeDensity * DisplayMetrics.DENSITY_DEFAULT);
        dm.scaledDensity = relativeDensity * (ORIGINAL_SCALED_DENSITY / ORIGINAL_DENSITY);
    }

}
