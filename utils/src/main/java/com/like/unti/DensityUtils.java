package com.like.unti;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 常用单位转换
 * @ author : Like on 2017/2/21/021.
 */

public class DensityUtils {

    private DensityUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int dp2px(int dp){
        return Math.round(dp*DENSITY);
    }

    /**
     * dp转px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context , float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

}
