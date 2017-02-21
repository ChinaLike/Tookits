package com.like.temp;

/**
 * Created by Like on 2017/2/17.
 */
public interface ITempView {

    int ERROR = 0xaff1;
    int DATA_NULL = 0xaff2;
    int LOADING = 0xaff3;

    /**
     * 设置填充界面类型
     *
     * @param type {@link ITempView#ERROR}
     *             {@link ITempView#DATA_NULL}
     *             {@link ITempView#LOADING}
     */
    void setType(int type);

    /**
     * 在这处理type = ITempView#ERROR 时的逻辑
     */
    void onError();

    /**
     * 在这处理type = ITempView#DATA_NULL 时的逻辑
     */
    void onNull();

    /**
     * 在这处理type = ITempView#LOADING 时的逻辑
     */
    void onLoading();

}
