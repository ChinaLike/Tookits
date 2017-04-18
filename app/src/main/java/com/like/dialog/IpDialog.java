package com.like.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.like.R;
import com.like.core.AbsDialogFragment;
import com.like.databinding.DialogAbsBinding;
import com.like.module.BindingModule;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ describe:
 * @ author: Like on 2017-03-31.
 * @ email: 572919350@qq.com
 */

public class IpDialog extends AbsDialogFragment<DialogAbsBinding> {
    @Bind(R.id.enter)
    Button mEnter;
    @Bind(R.id.cancel)
    Button mCancel;

    public IpDialog(Object object){
        super(object);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getModule(BindingModule.class).getDialogBindingTest();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_abs;
    }

    @Override
    protected void dataCallback(int result, Object data) {
        getBinding().setStr(data + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.enter, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.enter:
                //将数据回调给寄主
                getSimplerModule().onDialog(0, "对话框确认");
                break;
            case R.id.cancel:
                getSimplerModule().onDialog(1, "对话框取消");
                break;
        }
        dismiss();
    }
}
