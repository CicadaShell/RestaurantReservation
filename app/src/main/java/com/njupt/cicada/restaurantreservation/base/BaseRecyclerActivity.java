package com.njupt.cicada.restaurantreservation.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cicada.library.base.BasePresenter;
import com.cicada.library.pojo.PageBean;
import com.cicada.library.utils.InputMethodUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.widget.EmptyLayout;
import com.njupt.cicada.restaurantreservation.widget.RecyclerRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangfei on 2017/4/11.
 * https://github.com/DavidInChina/XRecyclerView.git
 */

public abstract class BaseRecyclerActivity<T extends BasePresenter, E> extends BaseActivity<T>
        implements RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener {

    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<E> mAdapter;

    protected boolean isRefreshing;

    protected PageBean<T> mBean;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected RecyclerRefreshLayout mRefreshLayout;
    @BindView(R.id.errorLayout)
    protected EmptyLayout mErrorLayout;
    @BindView(R.id.layoutListContainer)
    protected LinearLayout mLayoutListContainer;

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

    @Override
    public void initView() {
        mBean = new PageBean<>();
        mAdapter = getRecyclerAdapter();
        initRecycler();
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRefreshLayout.setVisibility(View.GONE);
                onRefreshing();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && mContext != null
                        && BaseRecyclerActivity.this.getCurrentFocus() != null) {
                    InputMethodUtils.hide(BaseRecyclerActivity.this);
                }
            }
        });
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mRefreshLayout.setVisibility(View.GONE);
            if (isLoadingData()) {
                onRefreshing();
            }
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            if (isLoadingData()) {
                mRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(true);
                        onRefreshing();
                    }
                });
            }
        }
    }

    protected void initRecycler() {
        mRecyclerView.setLayoutManager(getLayoutManager());
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    protected abstract BaseRecyclerAdapter<E> getRecyclerAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    protected GridLayoutManager getLayoutManager(int spanCount) {
        return new GridLayoutManager(mContext, spanCount);
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }

    /**
     * 是否进入页面就加载数据
     *
     * @return isNeedEmptyView
     */
    protected boolean isLoadingData() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_recycler;
    }

    @Override
    public void onRefreshing() {
        if (isRefreshing)
            return;
        isRefreshing = true;
        mBean.setPage(0);
        requestData();
    }

    @Override
    public void onLoadMore() {
        if (mAdapter.getState() != BaseRecyclerAdapter.STATE_NO_MORE) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
            mBean.setPage(mBean.getPage()+mBean.getCount());
            requestData();
        } else {
            mRefreshLayout.onComplete();
        }
    }

    protected void requestData() {

    }

    protected void onDataSuccess(List<E> list) {
        onComplete();
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (list != null && !list.isEmpty()) {
            if (mBean.getPage() == 0) {
                mAdapter.setItems(list);
            } else {
                mAdapter.addAll(list);
            }
            if (list.size() == mBean.getCount()) {
                mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_MORE, true);
            } else {
                mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            }

        } else {
            if (mBean.getPage() == 0) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            }
        }
    }

    protected void onDataError() {
        onComplete();
        if (mBean.getPage() == 0) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onComplete() {
        mRefreshLayout.setVisibility(View.VISIBLE);
        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
