package com.like.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.like.R;
import com.like.adapter.SlideAdapter;
import com.like.bean.SlideBean;
import com.like.listenner.OnItemClickListenner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements OnItemClickListenner {


    @InjectView(R.id.main_context)
    FrameLayout mainContext;
    @InjectView(R.id.main_slide)
    RecyclerView mainSlide;
    /**
     * 侧滑布局适配器
     */
    private SlideAdapter mSlideAdapter;

    private List<SlideBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    private void init() {
        mSlideAdapter = new SlideAdapter(MainActivity.this, mList);
    }

    @Override
    public void onClick(View view, int position) {

    }
}
