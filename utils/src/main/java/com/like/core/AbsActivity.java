package com.like.core;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.like.module.IOCProxy;

/**
 * @ describe: 所有的Activity都将集成本类
 * @ author: Like on 2017-02-04.
 * @ email: 572919350@qq.com
 */

public abstract class AbsActivity<VB extends ViewDataBinding> extends AppCompatActivity {
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
    }

    protected void initialization() {
        mAm = AbsFrame.getInstance();
        mAm.addActivity(this);
        mBind = DataBindingUtil.setContentView(this, setLayoutId());

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

}
