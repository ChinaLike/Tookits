package com.like.core;

import android.content.Context;

import com.like.module.AbsModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Module共享工厂
 *
 * @ auther : Like on 2017/2/17/017.
 */

public class ModuleFactory {
    private final String TAG = "ModuleFactory";

    private Map<Integer, AbsModule> mModules = new ConcurrentHashMap<>();

    private ModuleFactory() {

    }

    /**
     * 需要保证每个对象都有独立的享元工厂
     *
     * @return
     */
    public static ModuleFactory newInstance() {
        return new ModuleFactory();
    }

    /**
     * 获取Module
     *
     * @param context
     * @param clazz
     * @param <M>
     * @return
     */
    public <M extends AbsModule> M getModule(Context context, Class<M> clazz) {
        M module = (M) mModules.get(clazz.hashCode());
        if (module == null) {
            return newInstanceModule(context, clazz);
        }
        return module;
    }

    /**
     * 构造一个新的Module
     */
    private <T extends AbsModule> T newInstanceModule(Context context, Class<T> clazz) {
        Class[] paramTypes = {Context.class};
        Object[] params = {context};
        try {
            //获取构造方法和参数，这里用参数就可以指定调用的是哪个构造方法了
            Constructor<T> con = clazz.getConstructor(paramTypes);
            con.setAccessible(true);
            T module = con.newInstance(params);
            mModules.put(clazz.hashCode(), module);
            return module;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
