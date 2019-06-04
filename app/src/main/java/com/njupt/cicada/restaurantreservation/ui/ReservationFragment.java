package com.njupt.cicada.restaurantreservation.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cicada.library.utils.DBUtils;
import com.cicada.library.utils.RxManager;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.adapter.ReservationRecycleAdapter;
import com.njupt.cicada.restaurantreservation.base.BaseFragment;
import com.njupt.cicada.restaurantreservation.base.BaseRecyclerAdapter;
import com.njupt.cicada.restaurantreservation.constants.RxJavaConstants;
import com.njupt.cicada.restaurantreservation.entity.Reservation;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;
import com.njupt.cicada.restaurantreservation.pojo.ReservationCount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author guocongcong
 */
public class ReservationFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RxManager mRxManager;
    private ReservationRecycleAdapter mReservationAdapter;

    private int type = -1;

    public static ReservationFragment newInstance() {
        return new ReservationFragment();
    }

    public boolean isScrollTop() {
        return recyclerView != null && recyclerView.computeVerticalScrollOffset() == 0;
    }

    @Override
    protected void initIntentValue(Bundle bundle) {
        super.initIntentValue(bundle);
        type = getArguments().getInt("timeType");
    }

    @Override
    public void initView() {

        mReservationAdapter = new ReservationRecycleAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mReservationAdapter);
        mReservationAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                startActivity(ReservationDetailActivity.createIntent(mContext, mReservationAdapter.getItem(position).getId()));
            }
        });

        syncReservationData();

        mRxManager = new RxManager();
        mRxManager.on(RxJavaConstants.RX_RESERVATION_CHANGE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                syncReservationData();
            }
        });
        mRxManager.on(RxJavaConstants.RX_DATE_CHANGE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                syncReservationData();
            }
        });
    }

    public void syncReservationData() {
        List<Reservation> reservationList = queryReservationOrderBy();

        ReservationCount reservationCount = new ReservationCount();
        for (Reservation reservation : reservationList) {
            if (reservation.getStatus() != ReservationStatusEnum.CANCEL.getValue()
                    && reservation.getNumOfPeople() > 4) {
                reservationCount.addBigCount();
            } else if (reservation.getStatus() != ReservationStatusEnum.CANCEL.getValue()
                    && reservation.getNumOfPeople() > 0) {
                reservationCount.addSmallCount();
            }
        }
        mReservationAdapter.setReservationCount(reservationCount);
        mReservationAdapter.setItems(reservationList);
    }

    private ArrayList<Reservation> queryReservationOrderBy() {
        String[] field = new String[]{"year", "month", "day", "time_type"};
        String[] value = new String[]{String.valueOf(MainActivity.year), String.valueOf(MainActivity.month),
                String.valueOf(MainActivity.day), String.valueOf(type)};
        QueryBuilder builder = new QueryBuilder(Reservation.class);
        builder.whereEquals(field[0], value[0]);
        for (int i = 1; i < field.length; i++) {
            builder.whereAppendAnd().whereEquals(field[i], value[i]);
        }
        builder.appendOrderAscBy("status");
        return DBUtils.getLiteOrm().query(builder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRxManager.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_reservation;
    }

}
