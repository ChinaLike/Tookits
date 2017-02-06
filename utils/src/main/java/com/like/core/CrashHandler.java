package com.like.core;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.like.http.HttpUtil;
import com.like.unti.AndroidUtils;
import com.like.unti.CalendarUtils;
import com.like.unti.FileUtil;
import com.like.unti.NetUtils;
import com.like.unti.show.FL;
import com.like.unti.show.L;
import com.like.unti.show.T;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @ describe: 异常捕获
 * @ author: Like on 2017-02-06.
 * @ email: 572919350@qq.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = "CrashHandler";
    private final static Object LOCK = new Object();
    private static volatile CrashHandler INSTANCE = null;
    /**
     * 缺省异常
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    /**
     * 需要上传服务器的地址和参数
     */
    private String mServerHost, mPramKey;
    /**
     * 保存异常的文件名称
     */
    private String mExceptionFileName = "AbsExceptionFile.crash";

    private CrashHandler(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * 获取CrashHandler的实例，单例模式
     *
     * @param context
     * @return
     */
    public static CrashHandler getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 开启异常捕获
     * 需要配置网络请求权限
     *
     * @param serverHost 服务器地址
     * @param key        服务器键值
     */
    public void setServerHost(String serverHost, String key) {
        mServerHost = serverHost;
        mPramKey = key;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }

    /**
     * 处理捕获的异常
     *
     * @param ex
     * @return
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                T.showLong(mContext, "很抱歉，程序出现异常，即将退出");
                Looper.loop();
            }
        }.start();
        sendExceptionInfo(ex);
        return true;
    }

    /**
     * 发送异常给服务器
     *
     * @param ex
     */
    private void sendExceptionInfo(final Throwable ex) {
        ExceptionInfo info = new ExceptionInfo();
        info.time = CalendarUtils.getNowDataTime();
        info.versionCode = AndroidUtils.getVersionCode(mContext);
        info.versionName = AndroidUtils.getVersionName(mContext);
        info.systemVersionCode = Build.VERSION.SDK_INT;
        info.phoneModel = Build.MODEL;
        info.exceptionMsg = FL.getExceptionString(ex);
        if (AndroidUtils.checkPermission(mContext, Manifest.permission.INTERNET) &&
                AndroidUtils.checkPermission(mContext, Manifest.permission.ACCESS_NETWORK_STATE)) {
            if (NetUtils.isConnected(mContext) && !TextUtils.isEmpty(mServerHost) && !TextUtils.isEmpty(mPramKey)) {
                String objStr = new Gson().toJson(info);
                HttpUtil util = HttpUtil.getInstance(mContext);
                Map<String, String> params = new WeakHashMap<>();
                params.put(mPramKey, objStr);
                util.get(mServerHost, params, new HttpUtil.AbsResponse());
            }

        } else {
            L.e(TAG, "请在manifest文件定义android.permission.INTERNET和android.permission.ACCESS_NETWORK_STATE权限");
            return;
        }

        File file = new File(mContext.getCacheDir().getPath() + "/crash/" + mExceptionFileName);
        if (!file.exists()) {
            FileUtil.createFile(file.getPath());
        }
        writeExceptionToFile(info.exceptionMsg, file);

    }

    /**
     * 将异常日志写入文件
     */
    private void writeExceptionToFile(String message, File crashFile) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(CalendarUtils.getNowDataTime());
        stringBuffer.append("\n");
        stringBuffer.append(message);
        stringBuffer.append("\n");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(crashFile, true));
            writer.append(stringBuffer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    private class ExceptionInfo {
        int versionCode;
        String versionName;
        int systemVersionCode;
        String exceptionMsg;
        String phoneModel;
        String time;
    }

}
