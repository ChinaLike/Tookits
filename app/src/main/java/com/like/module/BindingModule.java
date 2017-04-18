package com.like.module;

import android.content.Context;

import com.like.databinding.ActivityAbsBinding;
import com.like.databinding.DialogAbsBinding;

/**
 * @ describe:
 * @ author: Like on 2017-03-31.
 * @ email: 572919350@qq.com
 */

public class BindingModule extends AbsModule{
    public BindingModule(Context context) {
        super(context);
    }

    public void setBinding(){
        getBinding(ActivityAbsBinding.class).setText("在Module中设置参数绑定");
    }

    public void getDialogBindingTest(){
        getBinding(DialogAbsBinding.class).setStr("dialog binding 测试");
    }

}
