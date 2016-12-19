package com.like.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 万能适配器
 *
 * @ auther : Like on 2016/12/13/013.
 */

public abstract class BaseCommAdapter<T> extends BaseAdapter {

    private List<T> mList;

    private Context mContext;

    public BaseCommAdapter(List<T> mList,Context mContext) {
        this.mList = mList;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=ViewHolder.newsInstance(convertView,mContext,getLayoutId());
        setUI(holder,position,mContext);
        return holder.getmConvertView();
    }

    protected abstract void setUI(ViewHolder holder, int position, Context context);

    protected abstract int getLayoutId();

    public static class ViewHolder {
        /**
         * 保存所有itemview的集合
         */
        private SparseArray<View> mViews;
        private View mConvertView;

        public ViewHolder(Context mContext, int layoutId) {
            mConvertView = View.inflate(mContext, layoutId, null);
            mConvertView.setTag(this);
            mViews = new SparseArray<>();
        }

        public static ViewHolder newsInstance(View convertView, Context context, int layoutId) {
            if (convertView == null) {
                return new ViewHolder(context, layoutId);

            } else {
                return (ViewHolder) convertView.getTag();
            }
        }

        /**
         * 获取根视图
         * @return
         */
        public View getmConvertView() {
            return mConvertView;
        }

        /**
         * 获取节点view
         * @param id
         * @param <T>
         * @return
         */
        @SuppressWarnings("unchecked")
        public <T extends View> T getItemView(int id)
        {
            View view =  mViews.get(id);
            if (view == null)
            {
                view = mConvertView.findViewById(id);
                mViews.append(id, view);
            }
            return (T) view;
        }

    }

}
