package com.like.temp;

import android.view.View;

/**
 * @ auther : Like on 2017/2/17/017.
 */

public interface OnTempBtClickListener {
    /**
     * @param type {@link ITempView#ERROR}, {@link ITempView#DATA_NULL}, {@link ITempView#LOADING}
     */
    void onBtTempClick(View view, int type);
}
