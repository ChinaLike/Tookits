package com.like.base;

import com.like.core.AbsFrame;

/**
 * @ author : Like on 2017/2/23/023.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //        AbsFrame.init(this).openCrashHandler();
//        AbsFrame.init(this).openCrashHandler("http://192.168.2.183/server.php", "params");
        AbsFrame.init(this);
    }
}
