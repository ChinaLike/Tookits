package com.like.module.inf;

/**
 * @ describe: Module监听
 * @ author: Like on 2017-02-06.
 * @ email: 572919350@qq.com
 */

public interface ModuleListener {
    /**
     * 无参的回调
     *
     * @param method 方法名
     */
    void callback(String method);

    /**
     * 带参数的回调
     *
     * @param method        方法名
     * @param dataClassType 参数类型
     * @param data          数据
     */
    void callback(String method, Class<?> dataClassType, Object data);

    /**
     * 统一接口的回调，回调接口为dataCallback
     *
     * @param result 返回码
     * @param data   回调数据
     */
    void callback(int result, Object data);
}
