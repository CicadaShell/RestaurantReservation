package com.njupt.cicada.restaurantreservation.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.haibin.calendarview.CalendarLayout;
import com.njupt.cicada.restaurantreservation.adapter.PageFragmentAdapter;
import com.njupt.cicada.restaurantreservation.ui.ReservationFragment;

/**
 * 如果嵌套各种View出现事件冲突，可以实现这个方法即可
 */
public class CalendarLinearLayout extends LinearLayout implements CalendarLayout.CalendarScrollView {

    private PageFragmentAdapter mAdapter;

    public CalendarLinearLayout(Context context) {
        super(context);
    }

    public CalendarLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 如果你想让下拉无效，return false
     *
     * @return isScrollToTop
     */
    @Override
    public boolean isScrollToTop() {
        if(mAdapter == null){
            if (getChildCount() > 1 && getChildAt(1) instanceof ViewPager) {
                ViewPager viewPager = (ViewPager) getChildAt(1);
                mAdapter = (PageFragmentAdapter) viewPager.getAdapter();
            }
        }
        return mAdapter != null && ((ReservationFragment) mAdapter.getCurFragment()).isScrollTop();
    }

}
