package com.like.fragment;

import android.os.Bundle;

import com.like.R;
import com.like.base.BaseFragment;
import com.like.databinding.FragmentPermissionBinding;

/**
 * @ describe: 权限测试
 * @ author: Like on 2017-03-19.
 * @ email: 572919350@qq.com
 */

public class PermissionTest extends BaseFragment<FragmentPermissionBinding> {


    public static PermissionTest newInstance() {
        Bundle bundle = new Bundle();
        PermissionTest fragment = new PermissionTest();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_permission;
    }
}
