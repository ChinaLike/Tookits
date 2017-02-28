package com.like.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.like.R;
import com.like.core.AbsActivity;

/**
 * @ author : Like on 2017/2/23/023.
 */

public abstract class BaseActivity<VB extends ViewDataBinding> extends AbsActivity<VB> {

    Toolbar mToolbar;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
}
