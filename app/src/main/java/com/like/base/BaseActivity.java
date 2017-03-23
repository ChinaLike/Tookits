package com.like.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        mToolbar.setNavigationIcon(R.drawable.arrow_left);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
