package com.njupt.cicada.restaurantreservation.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cicada.library.utils.DBUtils;
import com.cicada.library.utils.RxManager;
import com.cicada.library.utils.TimeUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.adapter.ReservationRecycleAdapter;
import com.njupt.cicada.restaurantreservation.base.ToolbarActivity;
import com.njupt.cicada.restaurantreservation.constants.RxJavaConstants;
import com.njupt.cicada.restaurantreservation.entity.Reservation;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;
import com.njupt.cicada.restaurantreservation.enums.ReservationTimeEnum;
import com.njupt.cicada.restaurantreservation.widget.SetArrivalDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public class ReservationDetailActivity extends ToolbarActivity {

    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.rlPhone)
    RelativeLayout rlPhone;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvNumOfPeople)
    TextView tvNumOfPeople;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvArrivalTime)
    TextView tvArrivalTime;
    @BindView(R.id.llArrival)
    LinearLayout llArrival;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    @BindView(R.id.btnCancel)
    TextView btnCancel;
    @BindView(R.id.btnArrival)
    TextView btnArrival;
    @BindView(R.id.tvTable)
    TextView tvTable;

    private RxManager mRxManager;
    private SetArrivalDialog mSetArrivalDialog;

    private int id;
    private Reservation mReservation;

    public static Intent createIntent(Context context, int id) {
        Intent intent = new Intent(context, ReservationDetailActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void initIntentValue() {
        super.initIntentValue();
        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tvTitle.setText("预约详情");
        ivEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {
        super.initView();

        initData();

        mSetArrivalDialog = new SetArrivalDialog(mContext, new SetArrivalDialog.OnDialogClick() {
            @Override
            public void onSureClick(long time, int table) {
                mReservation.setStatus(ReservationStatusEnum.SEAT.getValue());
                mReservation.setArrivalTime(time);
                mReservation.setTableNum(table);
                DBUtils.update(mReservation);
                initData();
                mRxManager.post(RxJavaConstants.RX_RESERVATION_CHANGE, "");
            }
        });

        mRxManager = new RxManager();
        mRxManager.on(RxJavaConstants.RX_RESERVATION_CHANGE, new Action1<Object>() {
            @Override
            public void call(Object o) {
                initData();
            }
        });
    }

    private void initData() {
        List<Reservation> reservationList = DBUtils.getQueryByWhere(Reservation.class, "id", String.valueOf(id));
        if (reservationList != null && reservationList.size() > 0) {
            mReservation = reservationList.get(0);
            tvName.setText(mReservation.getName());
            tvPhone.setText(mReservation.getPhone());
            tvTime.setText(mReservation.getTimeType() == ReservationTimeEnum.NOON.getValue() ? "中午" : "晚上");
            tvNumOfPeople.setText(mReservation.getNumOfPeople() + "人");
            tvStatus.setText(ReservationRecycleAdapter.switchString(mReservation.getStatus()));
            tvRemark.setText(TextUtils.isEmpty(mReservation.getRemark()) ? "无" : mReservation.getRemark());

            if (mReservation.getStatus() == ReservationStatusEnum.SEAT.getValue()) {
                llArrival.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnArrival.setVisibility(View.VISIBLE);
                btnArrival.setText("换桌");
                tvArrivalTime.setText(TimeUtils.longToString(mReservation.getArrivalTime(), TimeUtils.TIME_FORMAT));
                tvTable.setText(mReservation.getTableNum() + "桌");
            } else if (mReservation.getStatus() == ReservationStatusEnum.CANCEL.getValue()) {
                llArrival.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnArrival.setVisibility(View.GONE);
            } else {
                llArrival.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                btnArrival.setVisibility(View.VISIBLE);
                btnArrival.setText("入座");
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mReservation.getYear());
            stringBuilder.append("-");
            stringBuilder.append(mReservation.getMonth());
            stringBuilder.append("-");
            stringBuilder.append(mReservation.getDay());
            tvDate.setText(stringBuilder.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reservation_detail;
    }

    @OnClick({R.id.ivEdit, R.id.rlPhone, R.id.btnCancel, R.id.btnArrival})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivEdit:
                startActivity(EditReservationActivity.createIntent(mContext, id));
                break;
            case R.id.rlPhone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mReservation.getPhone()));
                startActivity(intent);
                break;
            case R.id.btnCancel:
                mReservation.setStatus(ReservationStatusEnum.CANCEL.getValue());
                DBUtils.update(mReservation);
                mRxManager.post(RxJavaConstants.RX_RESERVATION_CHANGE, "");
                finish();
                break;
            case R.id.btnArrival:
                mSetArrivalDialog.show();
                break;
            default:
                break;
        }
    }

}
