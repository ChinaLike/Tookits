package com.like;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.like.utils.common.BaseCommAdapter;
import com.like.utils.layout.TagCloudLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TagCloudLayout layout;
    private TagBaseAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (TagCloudLayout) findViewById(R.id.tag);
        mList = new ArrayList<>();
        mList.add("中华人名共和国");
        mList.add("大韩民国");
        mList.add("日本");
        mList.add("朝鲜");
        mList.add("台湾");
        mList.add("香港特别行政区");
        mList.add("澳门特别行政区");
        mList.add("越南");
        mList.add("老挝");
        mList.add("柬埔寨");
        mList.add("泰国");
        mList.add("缅甸");
        mList.add("马来西亚");
        mList.add("新加坡");
        mList.add("印度尼西亚");
        mList.add("文莱");
        mList.add("菲律宾");
//        mAdapter = new TagBaseAdapter(this,mList);
        layout.setAdapter(new BaseCommAdapter<String>(mList,this) {
            @Override
            protected void setUI(ViewHolder holder, int position, Context context) {
                Button mButton=holder.getItemView(R.id.tag_btn);
                mButton.setText(getItem(position));
            }

            @Override
            protected int getLayoutId() {
                return R.layout.tagview;
            }
        });
    }
}
