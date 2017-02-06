package com.like.core;

import android.app.ActivityManager;
import android.content.Context;

import com.like.unti.show.FL;

import java.util.Stack;

/**
 * @ describe: APP生命周期管理
 * @ author: Like on 2017-02-06.
 * @ email: 572919350@qq.com
 */

public class AbsFrame {
    private final static String TAG = "AbsFrame";
    private final static Object LOCK = new Object();
    private volatile static AbsFrame mManager = null;
    private Context mContext;
    private Stack<AbsActivity> mActivityStack = new Stack<>();

    private AbsFrame() {

    }

    private AbsFrame(Context context) {
        mContext = context;
    }

    /**
     * 初始化框架
     *
     * @param applicationContext 需要Application中初始化
     * @return
     */
    public static AbsFrame init(Context applicationContext) {
        if (mManager == null) {
            synchronized (LOCK) {
                if (mManager == null) {
                    mManager = new AbsFrame(applicationContext);
                }
            }
        }
        return mManager;
    }

    /**
     * 获取AppManager管流程实例
     *
     * @return
     */
    public static AbsFrame getInstance() {
        if (mManager == null) {
            throw new NullPointerException("请在application 的 onCreate 方法里面调用AbsFrame.init()方法进行初始化操作");
        }
        return mManager;
    }

    /**
     * 获取Activity栈
     *
     * @return
     */
    public Stack<AbsActivity> getActivityStack() {
        return mActivityStack;
    }

    /**
     * 开启本地异常捕获
     * 日志文件位于/data/data/Package Name/cache//crash/AbsExceptionFile.crash
     */
    public void openCrashHandler() {
        openCrashHandler("", "");
    }

    /**
     * 开启网络异常捕获
     * 需要网络权限，get请求，异常参数，需要下面两个网络权限
     * android:name="android.permission.INTERNET"
     * android:name="android.permission.ACCESS_NETWORK_STATE"
     *
     * @param serverHost 服务器地址
     * @param key        数据传输键值
     */
    public AbsFrame openCrashHandler(String serverHost, String key) {
        CrashHandler handler = CrashHandler.getInstance(mContext);
        handler.setServerHost(serverHost, key);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        return this;
    }

    /**
     * 获取堆栈大小
     *
     * @return
     */
    public int getActivitySize() {
        return mActivityStack.size();
    }

    /**
     * 获取指定的Activity
     *
     * @param location 下标
     * @return
     */
    public AbsActivity getActivity(int location) {
        return mActivityStack.get(location);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(AbsActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity，堆栈最后一个压入的Activity
     *
     * @return
     */
    public AbsActivity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(mActivityStack.lastElement());
    }

    /**
     * 结束指定的Activity
     */
    public void     finishActivity(AbsActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(AbsActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for (AbsActivity activity :mActivityStack){
            if (activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (mActivityStack.get(i) != null && mActivityStack.size() > 0) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行
     */
    public void exitApp(Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(mContext.getPackageName());
        } catch (Exception e) {
            FL.e(TAG, FL.getExceptionString(e));
        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }


}
