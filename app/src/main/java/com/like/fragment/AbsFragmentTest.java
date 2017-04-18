package com.like.fragment;

import android.os.Bundle;

import com.like.R;
import com.like.base.BaseFragment;
import com.like.databinding.FragmentTestBinding;

/**
 * Fragment测试
 *
 * @ author : Like on 2017/2/28/028.
 */

public class AbsFragmentTest extends BaseFragment<FragmentTestBinding> {


    public static AbsFragmentTest newInstance() {
        Bundle bundle = new Bundle();
        AbsFragmentTest fragment = new AbsFragmentTest();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_test;
    }
}
