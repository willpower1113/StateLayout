package com.willpower.state.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {
    /**
     * 屏幕宽度
     */
    public static int screenWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int screenHeight(Context context){
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, int sp) {
        return (int) (getDisplayMetrics(context).scaledDensity * sp + 0.5);
    }

    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) (getDisplayMetrics(context).density * dp + 0.5);
    }

    /**
     * 单位转换:px -> dp
     *
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / getDisplayMetrics(context).density + 0.5);
    }

    /**
     * 单位转换:px -> sp
     *
     * @param px
     * @return
     */
    public static int px2sp(Context context, int px) {
        return (int) (px / getDisplayMetrics(context).density + 0.5);
    }

    /**
     * 获取 DisplayMetrics
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
