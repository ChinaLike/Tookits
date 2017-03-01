package com.like.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.like.R;
import com.like.base.BaseActivity;
import com.like.databinding.ActivityAbsBinding;

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
        setTitle("AbsActivity测试");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_abs;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }

    @OnClick({R.id.loading_btn, R.id.loading_fail_btn, R.id.loading_null_btn, R.id.loading_style_btn})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.loading_btn:
                /**加载中*/
                break;
            case R.id.loading_fail_btn:
                /**加载失败*/
                break;
            case R.id.loading_null_btn:
                /**加载空界面*/
                break;
            case R.id.loading_style_btn:
                /**自定义加载界面*/
                break;
        }
    }

}
