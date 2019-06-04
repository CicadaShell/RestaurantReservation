package com.njupt.cicada.restaurantreservation.base;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cicada.library.base.BasePresenter;
import com.cicada.library.utils.InputMethodUtils;
import com.cicada.library.utils.QMUIStatusBarHelper;
import com.njupt.cicada.restaurantreservation.R;

import butterknife.BindView;

/**
 * Created by zhangfei on 2017/3/27.
 */

public abstract class ToolbarActivity<T extends BasePresenter> extends BaseActivity<T> {

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @Nullable
    @BindView(R.id.tvActionTitle)
    public TextView tvTitle;

    /**
     * 初始化Toolbar
     */
    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToolbar.setNavigationIcon(R.mipmap.icon_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    InputMethodUtils.hide((Activity) mContext);
                }
            });
        }
    }

    /**
     * 初始化Window属性
     */
    @Override
    public void initWindow() {
        // 经测试在代码里直接声明透明状态栏更有效
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
    }

    @Override
    public void initView() {

    }

}
