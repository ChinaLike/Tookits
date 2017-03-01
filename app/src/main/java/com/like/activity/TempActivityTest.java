package com.like.activity;

import com.like.R;
import com.like.base.BaseActivity;
import com.like.databinding.ActivityTempBinding;

/**
 * 占位布局测试
 *
 * @ author : Like on 2017/3/1/001.
 */

public class TempActivityTest extends BaseActivity<ActivityTempBinding> {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_temp;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }
}
