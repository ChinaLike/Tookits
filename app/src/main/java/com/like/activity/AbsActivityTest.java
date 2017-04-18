package com.like.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.like.R;
import com.like.base.BaseActivity;
import com.like.databinding.ActivityAbsBinding;
import com.like.dialog.IpDialog;
import com.like.module.AbsModule;
import com.like.module.AddressModule;
import com.like.module.BindingModule;
import com.like.module.PhoneAdressModule;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * AbsActivity类测试
 *
 * @ author : Like on 2017/2/28/028.
 */

public class AbsActivityTest extends BaseActivity<ActivityAbsBinding> {

    @Bind(R.id.show_test_text)
    TextView text;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setTitle("AbsActivity");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_abs;
    }

    @Override
    protected void dataCallback(int result, Object data) {
        getBinding().setText(data.toString());
    }

    @OnClick({R.id.module1, R.id.module2, R.id.module3, R.id.module4})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.module1:
                /**普通Module使用*/
                getModule(PhoneAdressModule.class).getPhoneAdressInfo();
                break;
            case R.id.module2:
                /**Module的普通回调使用*/
                getModule(AddressModule.class, new AbsModule.OnCallBack() {
                    @Override
                    public void onSuccess(int result, Object success) {
                        if (result == 1) {
                            getBinding().setText(success.toString());
                        }
                    }

                    @Override
                    public void onError(int result, Object error) {

                    }
                }).getAddress();
                break;
            case R.id.module3:
                /**在Module中调用binding*/
                getModule(BindingModule.class).setBinding();
                break;
            case R.id.module4:
                /**Dialog回调*/
                IpDialog dialog = new IpDialog(this);
                dialog.show(getSupportFragmentManager(), "ip_dialog");
                break;
        }
    }

}
