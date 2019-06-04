package com.njupt.cicada.restaurantreservation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cicada.library.pojo.ViewPageInfo;
import com.cicada.library.utils.DBUtils;
import com.cicada.library.utils.RxManager;
import com.cicada.library.widget.PagerSlidingTabStrip;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.adapter.PageFragmentAdapter;
import com.njupt.cicada.restaurantreservation.base.BaseActivity;
import com.njupt.cicada.restaurantreservation.constants.AppConstants;
import com.njupt.cicada.restaurantreservation.constants.RxJavaConstants;
import com.njupt.cicada.restaurantreservation.entity.Reservation;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;
import com.njupt.cicada.restaurantreservation.enums.ReservationTimeEnum;
import com.njupt.cicada.restaurantreservation.enums.TableTypeEnum;
import com.njupt.cicada.restaurantreservation.pojo.ReservationCount;
import com.njupt.cicada.restaurantreservation.widget.CalendarLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * @Author guocongcong
 * @Date 2019/5/31
 * @Describe
 */
public class MainActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener,
        CalendarView.OnMonthChangeListener {

    @BindView(R.id.tv_month_day)
    TextView tvMonthDay;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_lunar)
    TextView tvLunar;
    @BindView(R.id.ib_calendar)
    ImageView ibCalendar;
    @BindView(R.id.tv_current_day)
    TextView tvCurrentDay;
    @BindView(R.id.fl_current)
    FrameLayout flCurrent;
    @BindView(R.id.rl_tool)
    RelativeLayout rlTool;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.pageTab)
    PagerSlidingTabStrip pageTab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.linearLayout)
    CalendarLinearLayout linearLayout;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;

    private RxManager mRxManager;
    private PageFragmentAdapter mTabsAdapter;

    public static int year = 0;
    public static int month = 0;
    public static int day = 0;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void initView() {

//        insertTestData();

        Calendar calendar = calendarView.getSelectedCalendar();
        year = calendar.getYear();
        month = calendar.getMonth();
        day = calendar.getDay();

        initCalendarView();
        initFragment();
        syncCalendarData();

        mRxManager = new RxManager();
        mRxManager.on(RxJavaConstants.RX_RESERVATION_CHANGE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                syncCalendarData();
            }
        });
    }

    private void insertTestData() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                2, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                3, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                1, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                8, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 4, "GUO", "17768755029",
                6, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));

        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                2, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                3, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                1, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                8, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                6, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));

        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                2, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                3, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                1, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                8, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));
        reservationList.add(new Reservation(2019, 6, 5, "GUO", "17768755029",
                6, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NIGHT.getValue()));

        reservationList.add(new Reservation(2019, 6, 8, "GUO", "17768755029",
                2, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 10, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 13, "GUO", "17768755029",
                3, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 18, "GUO", "17768755029",
                4, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 18, "GUO", "17768755029",
                1, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 17, "GUO", "17768755029",
                8, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 25, "GUO", "17768755029",
                6, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 18, "GUO", "17768755029",
                1, TableTypeEnum.SMALL.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 17, "GUO", "17768755029",
                8, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        reservationList.add(new Reservation(2019, 6, 25, "GUO", "17768755029",
                6, TableTypeEnum.BIG.getValue(), "", ReservationTimeEnum.NOON.getValue()));
        DBUtils.insertAll(reservationList);
    }

    private void syncCalendarData() {
        Calendar calendar = calendarView.getSelectedCalendar();
        calendarView.setSchemeDate(getCalendarData(calendar.getYear(), calendar.getMonth()));
    }

    private void initCalendarView() {
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnMonthChangeListener(this);
        tvYear.setText(String.valueOf(calendarView.getCurYear()));
        tvMonthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        tvLunar.setText("今日");
        tvCurrentDay.setText(String.valueOf(calendarView.getCurDay()));
    }

    private void initFragment() {
        if (mTabsAdapter == null) {
            mTabsAdapter = new PageFragmentAdapter(getSupportFragmentManager(), pageTab, viewPager);
            viewPager.setAdapter(mTabsAdapter);
            viewPager.setOffscreenPageLimit(2);
        }
        ArrayList<ViewPageInfo> viewPageInfoList = new ArrayList<>();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("timeType", ReservationTimeEnum.NOON.getValue());
        viewPageInfoList.add(new ViewPageInfo("中午", "1", ReservationFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("timeType", ReservationTimeEnum.NIGHT.getValue());
        viewPageInfoList.add(new ViewPageInfo("晚上", "2", ReservationFragment.class, bundle2));
        mTabsAdapter.removeAll();
        mTabsAdapter.addAllTab(viewPageInfoList);
        mTabsAdapter.notifyDataSetChanged();
    }

    private Map<String, Calendar> getCalendarData(int year, int month) {

        Map<String, Calendar> calendarMap = new HashMap<>();
        String[] field = new String[]{"year", "month", "day", "status"};
        String[] value = new String[]{String.valueOf(year), String.valueOf(month), "", ""};
        for (int i = 1; i <= 31; i++) {
            Calendar calendar = new Calendar();
            calendar.setYear(year);
            calendar.setMonth(month);
            calendar.setSchemeColor(0xFF13acf0);
            calendar.setDay(i);
            value[2] = String.valueOf(i);
            value[3] = String.valueOf(ReservationStatusEnum.UNDERWAY.getValue());
            int count = (int) DBUtils.getQuerySizeByWhere(Reservation.class, field, value);
            value[3] = String.valueOf(ReservationStatusEnum.SEAT.getValue());
            count += (int) DBUtils.getQuerySizeByWhere(Reservation.class, field, value);
            if (count >= (AppConstants.BIG_TABLE_COUNT + AppConstants.SMALL_TABLE_COUNT) * 2) {
                calendar.setScheme("满");
            } else {
                calendar.setScheme(String.valueOf(count));
            }

            if (count > 0) {
                Log.d(TAG, "getCalendarData: +++++++++++++++++" + count);
                calendarMap.put(calendar.toString(), calendar);
            }
        }

        return calendarMap;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvLunar.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        year = calendar.getYear();
        month = calendar.getMonth();
        day = calendar.getDay();
        tvYear.setText(String.valueOf(year));
        tvMonthDay.setText(month + "月" + day + "日");
        tvLunar.setText(calendar.getLunar());
        mRxManager.post(RxJavaConstants.RX_DATE_CHANGE, "");
    }

    @Override
    public void onMonthChange(int year, int month) {
        calendarView.setSchemeDate(getCalendarData(year, month));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }

    @OnClick({R.id.ivAdd, R.id.tv_month_day, R.id.fl_current})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAdd:
                startActivity(EditReservationActivity.createIntent(mContext));
                break;
            case R.id.tv_month_day:
                if (!calendarLayout.isExpand()) {
                    calendarLayout.expand();
                    return;
                }
                calendarView.showYearSelectLayout(calendarView.getSelectedCalendar().getYear());
                tvLunar.setVisibility(View.GONE);
                tvYear.setVisibility(View.GONE);
                break;
            case R.id.fl_current:
                calendarView.scrollToCurrent();
                break;
            default:
                break;
        }
    }

}
