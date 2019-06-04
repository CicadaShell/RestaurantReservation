package com.njupt.cicada.restaurantreservation.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by zhangfei on 16/9/20.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    private LayoutInflater mInflater;
    protected List<T> mDatas = new ArrayList<T>();

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int arg0) {
        if (mDatas.size() > arg0) {
            return mDatas.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addItem(int pos, T obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        notifyDataSetInvalidated();
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(getLayoutId(), null, false);
            BaseListViewHolder holder = getViewHolder(convertView);
            ButterKnife.bind(holder, convertView);
            initViewWhenNew(parent.getContext(), holder, position);
            convertView.setTag(holder);
        }
        initView((BaseListViewHolder) convertView.getTag(), position);

        return convertView;
    }

    abstract public int getLayoutId();

    abstract public BaseListViewHolder getViewHolder(View convertView);

    abstract public void initView(BaseListViewHolder holder, int position);

    /**
     * 当创建每一项的View时调用
     *
     * @param context
     * @param holder
     * @param position
     */
    protected void initViewWhenNew(Context context, BaseListViewHolder holder, int position) {

    }
}
