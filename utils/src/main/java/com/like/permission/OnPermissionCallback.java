package com.like.permission;

/**
 * Created by lyy on 2016/4/11.
 * 权限回调
 */
public interface OnPermissionCallback {
    int PERMISSION_ALERT_WINDOW = 0xad1;
    int PERMISSION_WRITE_SETTING = 0xad2;

    void onSuccess(String... permissions);

    void onFail(String... permissions);
}
