package com.like.unti;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * @ describe: 屏幕工具
 * @ author: Like on 2017-01-22.
 * @ email: 572919350@qq.com
 */

public class ScreenUntil {

    private volatile static ScreenUntil mUtil = null;
    private static final Object LOCK = new Object();

    private ScreenUntil() {

    }

    public static ScreenUntil getInstance() {
        if (mUtil == null) {
            synchronized (LOCK) {
                if (mUtil == null) {
                    mUtil = new ScreenUntil();
                }
            }
        }
        return mUtil;
    }

    /**
     * 设置View的灰度，即去掉一张图片中的所有饱和度
     *
     * @param v         视图
     * @param greyScale 是否设置
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setGreyScale(View v, boolean greyScale) {
        if (greyScale) {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            v.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        } else {
            v.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @param mContext
     * @return
     */
    public int getScreenWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param mContext
     * @return
     */
    public int getScreenHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @param mContext
     * @return
     */
    public int getStatusHeight(Context mContext) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        final View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        final View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 判断是否开启了自动亮度调节
     *
     * @param mContext
     * @return
     */
    public boolean isAutoBrightness(Context mContext) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(
                    mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    /**
     * 获取屏幕亮度
     *
     * @param activity
     * @return
     */
    public float getScreenBrightness(Activity activity) {
        int currBrightness = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            currBrightness = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return currBrightness;
    }

    /**
     * 关闭自动亮度调节
     *
     * @param activity
     */
    public void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 设置亮度
     *
     * @param activity
     * @param brightness
     */
    public void setBrightness(Activity activity, float brightness) {
        /**先关闭自动亮度调节*/
        stopAutoBrightness(activity);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = brightness * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 开启亮度自动调节
     */
    public void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 保存亮度设置状态
     */
    public void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = Settings.System
                .getUriFor("screen_brightness");
        Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        resolver.notifyChange(uri, null);
    }

}
