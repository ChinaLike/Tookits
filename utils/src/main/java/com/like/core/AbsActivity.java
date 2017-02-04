package com.like.core;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
