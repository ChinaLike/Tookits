package com.like.module;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.like.core.AbsActivity;
import com.like.core.BindingFactory;
import com.like.module.inf.ModuleListener;
import com.like.unti.StringUtil;
import com.like.unti.show.L;

/**
 * 抽象的Module
 * @ auther : Like on 2017/2/16/016.
 */

public class AbsModule {

    public String TAG = "";
    private Context mContext;
    private ModuleListener mModuleListener;
    private OnCallBack  mCallBack;
    private BindingFactory mBindingFactory;
    private Object mHost;

    public interface OnCallBack{
        void onSuccess(int result , Object success);
        void onError(int result , Object error);
    }

    public AbsModule (Context context){
        mContext = context;
        init();
    }

    private void init(){
        TAG = StringUtil.getClassName(this);
        mBindingFactory = BindingFactory.newInstance();
    }

    /**
     * 设置ModuleListener监听
     * @param moduleListener
     */
    public void setModuleListener(ModuleListener moduleListener){
        if (moduleListener == null){
            throw new NullPointerException("moduleListenner不能为空，请先实例");
        }
        this.mModuleListener = moduleListener;
    }

    /**
     * 为Binding设置寄生
     * @param host
     */
    public void setHost(Object host){
        mHost = host;
    }
    /**
     * 失败的回调
     */
    private void successCallback(int key , Object obj){
        if (mCallBack == null){
            L.e(TAG,"OnCallback为NULL");
            return;
        }
        mCallBack.onSuccess(key,obj);
    }
    /**
     * 失败的回调
     */
    public void errorCallback(int key, Object obj) {
        if (mCallBack == null) {
            L.e(TAG, "OnCallback 为 null");
            return;
        }
        mCallBack.onError(key, obj);
    }

    /**
     * 获取Context
     *
     * @return Context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 设置Module回调
     *
     * @param callback Module 回调
     */
    public void setCallback(OnCallBack callback) {
        mCallBack = callback;
    }

    /**
     * 获取ViewDataBinding
     *
     * @param clazz ViewDataBinding实例
     * @param <VB>1
     * @return
     */
    protected <VB extends ViewDataBinding> VB getBinding(Class<VB> clazz) {
        return mBindingFactory.getBinding(mHost, clazz);
    }

    /**
     * 统一的回调，如果已经设置了OnCallback，则使用OnCallback;
     * 否则将使用dataCallback，{@link AbsActivity#dataCallback(int, Object)}
     *
     * @param result 返回码
     * @param data   回调数据
     */
    protected void callback(final int result, final Object data) {
        if (mCallBack != null) {
            successCallback(result, data);
            return;
        }
        mModuleListener.callback(result, data);
    }

    /**
     * module回调
     *
     * @param method 回调的方法名
     */
    @Deprecated
    protected void callback(String method) {
        mModuleListener.callback(method);
    }

    /**
     * 带参数的module回调
     *
     * @param method        回调的方法名
     * @param dataClassType 回调数据类型
     * @param data          回调数据
     */
    @Deprecated
    protected void callback(String method, Class<?> dataClassType, Object data) {
        mModuleListener.callback(method, dataClassType, data);
    }

}
