package com.like.activity;

import android.os.Bundle;

import com.like.R;
import com.like.base.BaseActivity;
import com.like.databinding.ActivityAbsBinding;

/**
 * AbsActivity类测试
 *
 * @ author : Like on 2017/2/28/028.
 */

public class AbsActivityTest extends BaseActivity<ActivityAbsBinding> {

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setTitle("AbsActivity测试");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_abs;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }
}
