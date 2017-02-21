package com.like.core;

import android.databinding.ViewDataBinding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Binding工厂
 *
 * @ auther : Like on 2017/2/16/016.
 */

public class BindingFactory {
    private final String TAG = "BindingFactory";
    private Map<Integer, ViewDataBinding> mBinding = new ConcurrentHashMap<>();

    private BindingFactory() {

    }

    /**
     * 需要保证每个对象都有独立的享元工厂
     *
     * @return
     */
    public static BindingFactory newInstance() {
        return new BindingFactory();
    }

    /**
     * 获取Binding
     *
     * @param obj   Module寄生
     * @param clazz
     * @param <VB>
     * @return
     */
    public <VB extends ViewDataBinding> VB getBinding(Object obj, Class<VB> clazz) {
        VB vb = (VB) mBinding.get(clazz.hashCode());
        if (vb == null) {
            vb = loadBind(obj, clazz);
        }
        return vb;
    }

    private <VB extends ViewDataBinding> VB loadBind(Object obj, Class<VB> clazz) {
        VB vb = null;
        if (obj instanceof AbsActivity) {
            vb = (VB) ((AbsActivity) obj).getBinding();
        } else if (obj instanceof AbsFragment) {
            vb = (VB) ((AbsFragment) obj).getBinding();
        } else if (obj instanceof AbsDialogFragment) {
            vb = (VB) ((AbsDialogFragment) obj).getBinding();
        }
        mBinding.put(clazz.hashCode(), vb);
        return vb;
    }

}
