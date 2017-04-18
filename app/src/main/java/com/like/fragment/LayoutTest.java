package com.like.fragment;

import android.os.Bundle;

import com.like.R;
import com.like.base.BaseFragment;
import com.like.databinding.FragmentLayoutBinding;

/**
 * @ describe: 自定义控件
 * @ author: Like on 2017-03-19.
 * @ email: 572919350@qq.com
 */

public class LayoutTest extends BaseFragment<FragmentLayoutBinding> {

    public static LayoutTest newInstance() {
        Bundle bundle = new Bundle();
        LayoutTest fragment = new LayoutTest();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_layout;
    }
}
