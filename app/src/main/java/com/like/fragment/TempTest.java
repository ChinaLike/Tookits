package com.like.fragment;

import android.os.Bundle;

import com.like.R;
import com.like.base.BaseFragment;
import com.like.databinding.FragmentTempBinding;

/**
 * @ describe: 填充测试
 * @ author: Like on 2017-03-19.
 * @ email: 572919350@qq.com
 */

public class TempTest extends BaseFragment<FragmentTempBinding> {

    public static TempTest newInstance() {
        Bundle bundle = new Bundle();
        TempTest fragment = new TempTest();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_temp;
    }
}
