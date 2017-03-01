package com.like.core;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.like.module.AbsModule;
import com.like.module.IOCProxy;
import com.like.temp.AbsTempView;
import com.like.temp.OnTempBtClickListener;
import com.like.temp.TempView;
import com.like.unti.StringUtil;
import com.like.unti.show.T;

import butterknife.ButterKnife;

/**
 * @ describe: 所有的Activity都将集成本类
 * @ author: Like on 2017-02-04.
 * @ email: 572919350@qq.com
 */

public abstract class AbsActivity<VB extends ViewDataBinding> extends AppCompatActivity implements OnTempBtClickListener {
    /**
     * 打印标识
     */
    protected String TAG = "";
    /**
     * 绑定布局生成对象
     */
    protected VB mBind;
    /**
     * Activity生命周期管理
     */
    protected AbsFrame mAm;

    private IOCProxy mProxy;

    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;

    protected View mRootView;

    protected ModuleFactory mModuleF;
    /**
     * 占位布局
     */
    protected AbsTempView mTempView;
    /**
     * 是否使用占位布局
     */
    protected boolean useTempView = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        init(savedInstanceState);
    }

    protected void initialization() {
        mAm = AbsFrame.getInstance();
        mAm.addActivity(this);
        mBind = DataBindingUtil.setContentView(this, setLayoutId());
        mProxy = IOCProxy.newInstance(this);
        TAG = StringUtil.getClassName(this);
        mModuleF = ModuleFactory.newInstance();
        ButterKnife.bind(this);
        mRootView = mBind.getRoot();
        if (useTempView) {
            mTempView = new TempView(this);
            mTempView.setBtListener(this);
        }
    }

    /**
     * 获取填充View
     */
    protected AbsTempView getTempView() {
        return mTempView;
    }

    /**
     * 是否使用填充界面
     *
     * @param useTempView
     */
    protected void setUseTempView(boolean useTempView) {
        this.useTempView = useTempView;
    }

    /**
     * 设置自定义的TempView
     *
     * @param tempView
     */
    protected void setCustomTempView(AbsTempView tempView) {
        mTempView = tempView;
        mTempView.setBtListener(this);
    }

    /**
     * 显示占位布局
     *
     * @param type {@link TempView#ERROR}
     *             {@link TempView#DATA_NULL}
     *             {@link TempView#LOADING}
     */
    protected void showTempView(int type) {
        if (mTempView == null || !useTempView) {
            return;
        }
        mTempView.setVisibility(View.VISIBLE);
        mTempView.setType(type);
        setContentView(mTempView);
    }

    public ModuleFactory getModuleFactory() {
        return mModuleF;
    }

    /**
     * 关闭占位布局
     */
    protected void hintTempView() {
        hintTempView(0);
    }

    /**
     * 延时关闭占位布局
     */
    protected void hintTempView(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTempView == null || !useTempView) {
                    return;
                }
                //清除EditText的焦点
                mTempView.clearFocus();
                mTempView.setVisibility(View.GONE);
                setContentView(mRootView);
            }
        }, delay);
    }

    @Override
    public void onBtTempClick(View view, int type) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public void finish() {
        super.finish();
        mAm.removeActivity(this);
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置资源ID
     */
    protected abstract int setLayoutId();

    /**
     * 数据回调
     *
     * @param result
     * @param data
     */
    protected abstract void dataCallback(int result, Object data);


    /**
     * 获取binding对象
     */
    protected VB getBinding() {
        return mBind;
    }


    /**
     * 获取Module
     *
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz) {
        M module = mModuleF.getModule(this, clazz);
        module.setHost(this);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 获取Module
     *
     * @param clazz    Module class0
     * @param callback Module回调函数
     * @param <M>      {@link AbsModule}
     * @return
     */
    protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz, @NonNull AbsModule.OnCallBack callback) {
        M module = mModuleF.getModule(this, clazz);
        module.setCallback(callback);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 双击退出
     */
    private boolean onDoubleClickExit(long timeSpace) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace) {
            T.showShort(this, "再按一次退出");
            mFirstClickTime = currentTimeMillis;
            return false;
        } else {
            return true;
        }
    }

    /**
     * 双击退出，间隔时间为2000ms
     *
     * @return
     */
    public boolean onDoubleClickExit() {
        return onDoubleClickExit(2000);
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground) {
        mAm.exitApp(isBackground);
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        mAm.exitApp(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionHelp.getInstance().handleSpecialPermissionCallback(this, requestCode, resultCode, data);
    }

}
