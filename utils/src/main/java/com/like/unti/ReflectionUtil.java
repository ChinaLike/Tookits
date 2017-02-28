package com.like.unti;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.like.unti.show.L;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;

/**
 * 反射工具类
 *
 * @ auther : Like on 2017/2/11/011.
 */

public class ReflectionUtil {

    private final static String TAG = "ReflectionUtil";

    /**
     * 从SD卡读取layout文件
     *
     * @param context
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @return
     */
    public static XmlPullParser getLayoutXmlPullParser(Context context, String filePath, String fileName) {
        XmlPullParser xmlPullParser = null;
        AssetManager asset = context.getResources().getAssets();
        try {
            Method method = asset.getClass().getMethod("addAssetPath", String.class);
            int cookie = (Integer) method.invoke(asset, filePath);
            if (cookie == 0) {
                L.e(TAG, "加载路径失败");
            }
            xmlPullParser = asset.openXmlResourceParser(cookie, fileName + ".xml");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlPullParser;
    }

    /**
     * 获取类中所有字段，类中没有就寻找父类
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class clazz) {
        Field[] fields = null;
        /**Class 对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段*/
        fields = clazz.getDeclaredFields();
        /**如果本类没有就在父类找，递归寻找*/
        if (fields == null || fields.length == 0) {
            Class superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                fields = getFields(superClazz);
            }
        }
        return fields;
    }

    /**
     * 获取指定类中指定字段，没有就从父类查询
     *
     * @param clazz 类
     * @param name  字段
     * @return
     */
    public static Field getField(Class clazz, String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                field = clazz.getField(name);
            } catch (NoSuchFieldException e1) {
                if (clazz.getSuperclass() == null) {
                    return field;
                } else {
                    field = getField(clazz.getSuperclass(), name);
                }
            }
        }
        if (field != null) {
            /**类中的成员变量为private,故必须进行此操作*/
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param params     参数
     * @return 方法对象
     */
    public static Method getMethod(Class clazz, String methodName, final Class<?>... params) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, params);
            } catch (NoSuchMethodException e1) {
                if (clazz.getSuperclass() == null) {
                    L.e(TAG, "无法找到" + methodName + "方法");
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName, params);
                }
            }
        }
        if (method != null) {
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 加载指定的反射类
     *
     * @param context
     * @param className
     * @return
     */
    public static Class<?> loadClass(Context context, String className) {
        String packageName = AndroidUtils.getPackageName(context);
        String sourcePath = AndroidUtils.getSourcePath(context, packageName);
        if (!TextUtils.isEmpty(sourcePath)) {
            PathClassLoader loader = new PathClassLoader(sourcePath, "/data/app/", ClassLoader.getSystemClassLoader());
            try {
                return loader.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            L.e(TAG, "没有" + className + "目录");
        }
        return null;
    }
}
