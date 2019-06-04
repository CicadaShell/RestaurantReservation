package com.njupt.cicada.restaurantreservation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.base.BaseRecycleViewHolder;
import com.njupt.cicada.restaurantreservation.base.BaseRecyclerAdapter;
import com.njupt.cicada.restaurantreservation.constants.AppConstants;
import com.njupt.cicada.restaurantreservation.entity.Reservation;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;
import com.njupt.cicada.restaurantreservation.pojo.ReservationCount;

import butterknife.BindView;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public class ReservationRecycleAdapter extends BaseRecyclerAdapter<Reservation> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    private ReservationCount mReservationCount;

    public ReservationRecycleAdapter(Context context) {
        super(context, BaseRecyclerAdapter.ONLY_HEADER);
        setOnLoadingHeaderCallBack(this);
    }

    public void setReservationCount(ReservationCount reservationCount) {
        this.mReservationCount = reservationCount;
        notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new ReservationHeadHolder(mInflater.inflate(R.layout.layout_head_reservation, parent, false));
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationHeadHolder holder1 = (ReservationHeadHolder) holder;
        if (mReservationCount != null) {
            holder1.tvSmallReservedCount.setText(String.valueOf(mReservationCount.getSmallCount()));
            holder1.tvSmallUsableCount.setText(String.valueOf(AppConstants.SMALL_TABLE_COUNT - mReservationCount.getSmallCount()));
            holder1.tvBigReservedCount.setText(String.valueOf(mReservationCount.getBigCount()));
            holder1.tvBigUsableCount.setText(String.valueOf(AppConstants.BIG_TABLE_COUNT - mReservationCount.getBigCount()));
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ReservationHolder(mInflater.inflate(R.layout.item_reservation, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Reservation item, int position) {
        ReservationHolder holder1 = (ReservationHolder) holder;
        holder1.line.setImageLevel(item.getStatus());
        holder1.ivStatusType.setImageLevel(item.getStatus());
        holder1.tvStatus.setText(switchString(item.getStatus()));
        holder1.tvName.setText(item.getName());
        holder1.tvNumOfPeople.setText(item.getNumOfPeople() + "人");
        holder1.tvRemark.setText("备注：" + (TextUtils.isEmpty(item.getRemark()) ? "无" : item.getRemark()));
    }

    public static String switchString(int status) {
        switch (status) {
            case 0:
                return "预约中";
            case 1:
                return "已入座";
            case 2:
                return "已取消";
            default:
                return "";
        }
    }

    class ReservationHeadHolder extends BaseRecycleViewHolder {

        @BindView(R.id.tvSmallReservedCount)
        TextView tvSmallReservedCount;
        @BindView(R.id.tvSmallUsableCount)
        TextView tvSmallUsableCount;
        @BindView(R.id.tvBigReservedCount)
        TextView tvBigReservedCount;
        @BindView(R.id.tvBigUsableCount)
        TextView tvBigUsableCount;

        public ReservationHeadHolder(View itemView) {
            super(itemView);
        }
    }

    class ReservationHolder extends BaseRecycleViewHolder {

        @BindView(R.id.line)
        ImageView line;
        @BindView(R.id.ivStatusType)
        ImageView ivStatusType;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNumOfPeople)
        TextView tvNumOfPeople;
        @BindView(R.id.tvRemark)
        TextView tvRemark;

        public ReservationHolder(View itemView) {
            super(itemView);
        }
    }

}
