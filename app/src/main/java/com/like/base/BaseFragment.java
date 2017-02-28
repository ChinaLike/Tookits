package com.like.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.like.core.AbsFragment;

/**
 * @ author : Like on 2017/2/28/028.
 */

public abstract class BaseFragment<VB extends ViewDataBinding> extends AbsFragment<VB> {
    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }
}
