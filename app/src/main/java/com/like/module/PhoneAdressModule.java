package com.like.module;

import android.content.Context;

import com.like.http.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ describe:
 * @ author: Like on 2017-03-31.
 * @ email: 572919350@qq.com
 */

public class PhoneAdressModule extends AbsModule {

    private static final String URL = "http://apis.juhe.cn/mobile/get?phone=15983438888&key=b4afee679ae7c937c8172e13e5c0b115";

    public PhoneAdressModule(Context context) {
        super(context);
    }

    /**
     * 测试假数据
     */
    public void getPhoneAdressInfo() {
        try {
            JSONObject object = new JSONObject("{\"resultcode\":\"200\",\"reason\":\"Return Successd!\",\"result\":{\"province\":\"四川\",\"city\":\"凉山\",\"areacode\":\"\",\"zip\":\"615000\",\"company\":\"移动\",\"card\":\"\"},\"error_code\":0}");
            String address = object.getJSONObject("result").getString("province") + object.getJSONObject("result").getString("city");
            String company = object.getJSONObject("result").getString("company");
            callback(0, "电话号码15983438888的归属地是" + address + "的" + company);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
