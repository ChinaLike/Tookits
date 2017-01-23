package com.like.unti;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @ describe: 数据存储工具类
 * @ author: Like on 2017-01-21.
 * @ email: 572919350@qq.com
 */

public class SharePreUntil {

    /**
     * 读取字符串
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      字符串键值
     * @return 键值对应的字符串，默认返回""
     */
    public static String getString(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getString(key, "");
    }

    /**
     * 读取int数据
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      int的键值
     * @return 键值对应的int，默认返回-1
     */
    public static int getInt(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getInt(key, -1);
    }

    /**
     * 读取Boolean值
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      Boolean的键值
     * @return 键值对应的Boolean，默认返回false
     */
    public static Boolean getBoolean(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getBoolean(key, false);
    }

    /**
     * 读取Float值
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      Float的键值
     * @return 键值对应的Float，默认返回0.0F
     */
    public static Float getFloat(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getFloat(key, 0.0F);
    }

    /**
     * 读取Long值
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      Long的键值
     * @return 键值对应的Long，默认返回0L
     */
    public static Long getLong(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getLong(key, 0L);
    }

    /**
     * 读取所有的值
     *
     * @param preName  配置文件的名称
     * @param mContext
     * @return 返回所有的键值集合
     */
    public static Map<String, ?> getAll(String preName, Context mContext) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        return pre.getAll();
    }

    /**
     * 获取对象
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      键值
     * @param clazz    需要转换的对象
     * @param <T>
     * @return 对象
     */
    public static <T> T getObject(String preName, Context mContext, String key, Class<T> clazz) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        String str = pre.getString(key, "");
        return TextUtils.isEmpty(str) ? null : new Gson().fromJson(str, clazz);
    }

    /**
     * 移除键值对
     *
     * @param preName  配置文件名
     * @param mContext
     * @param key      键值
     */
    public static void removeKey(String preName, Context mContext, String key) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 存储字符串到配置文件
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      存储的键值
     * @param value    需要存储的字符串
     * @return 成功的标识
     */
    public static Boolean putString(String preName, Context mContext, String key, String value) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 存储数字到配置文件
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      存储的键值
     * @param value    需要存储的数字
     * @return 成功的标志
     */
    public static Boolean putInt(String preName, Context mContext, String key, int value) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 存储Float到配置文件
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      存储的键值
     * @param value    需要存储的Float
     * @return 成功的标识
     */
    public static Boolean putFloat(String preName, Context mContext, String key, float value) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 存储Boolean到配置文件
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      存储的键值
     * @param value    需要存储的Boolean
     * @return 成功的标识
     */
    public static Boolean putBoolean(String preName, Context mContext, String key, boolean value) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 存储Long到配置文件
     *
     * @param preName  配置文件名称
     * @param mContext
     * @param key      存储的键值
     * @param value    需要存储的Long
     * @return 成功的标识
     */
    public static Boolean putLong(String preName, Context mContext, String key, long value) {
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 存储对象到配置文件
     *
     * @param preName  配置文件的名称
     * @param mContext
     * @param key      存储的键值
     * @param clazz    存储的类
     * @param obj      存储的对象
     * @return 存储的标志
     */
    public static Boolean putObject(String preName, Context mContext, String key, Class<?> clazz, Object obj) {
        String str = new Gson().toJson(obj, clazz);
        SharedPreferences pre = mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(key, str);
        return editor.commit();
    }

}