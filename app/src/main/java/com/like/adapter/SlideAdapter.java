package com.like.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.R;
import com.like.bean.SlideBean;
import com.like.listenner.OnItemClickListenner;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @ describe:
 * @ author: Like on 2017-01-22.
 * @ email: 572919350@qq.com
 */

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolde> {

    private Context mContext;

    private List<SlideBean> mList;

    private OnItemClickListenner onItemClickListenner;

    public SlideAdapter(Context mContext, List<SlideBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SlideViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SlideViewHolde(LayoutInflater.from(mContext).inflate(R.layout.item_slidemenu, parent, false));
    }

    @Override
    public void onBindViewHolder(SlideViewHolde holder, final int position) {
        holder.slideName.setText(mList.get(position).getName());
        holder.slideImg.setImageResource(mList.get(position).getImageId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenner.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }

    static class SlideViewHolde extends RecyclerView.ViewHolder {
        @InjectView(R.id.slide_img)
        ImageView slideImg;
        @InjectView(R.id.slide_name)
        TextView slideName;

        public SlideViewHolde(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
