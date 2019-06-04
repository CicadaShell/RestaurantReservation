package com.njupt.cicada.restaurantreservation.base;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cicada.library.base.BasePresenter;
import com.cicada.library.base.BaseView;
import com.cicada.library.utils.TUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    public String TAG = this.getClass().getSimpleName();

    /**
     * 当前上下文
     */
    protected Context mContext;

    public T mPresenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentValue(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null && this instanceof BaseView) mPresenter.attachV(getActivity(), this);
        if (savedInstanceState != null) {
            Icepick.restoreInstanceState(this, savedInstanceState);
        } else {
            initIntentValue(getArguments());
        }
        View view = inflater.inflate(getLayoutId(), null);
        initWidget(view);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 初始化页面传值
     */
    protected void initIntentValue(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 获取布局文件的id
     *
     * @return
     */
    public abstract int getLayoutId();


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}