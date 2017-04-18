package com.like.module;

import android.content.Context;

/**
 * @ describe:
 * @ author: Like on 2017-03-31.
 * @ email: 572919350@qq.com
 */

public class AddressModule extends AbsModule{
    public AddressModule(Context context) {
        super(context);
    }

    public void getAddress(){
        callback(1,"地址：四川");
    }
}
