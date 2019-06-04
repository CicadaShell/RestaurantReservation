package com.njupt.cicada.restaurantreservation.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cicada.library.utils.ResourceUtils;
import com.cicada.library.utils.TimeUtils;
import com.cicada.library.utils.ToastUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.base.BaseDialog;
import com.njupt.cicada.restaurantreservation.entity.Reservation;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public class SetArrivalDialog extends BaseDialog {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvArrivalTime)
    TextView tvArrivalTime;
    @BindView(R.id.rlArrivalTime)
    RelativeLayout rlArrivalTime;
    @BindView(R.id.tvTable)
    TextView tvTable;
    @BindView(R.id.rlTable)
    RelativeLayout rlTable;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;
    @BindView(R.id.flArrivalTime)
    FrameLayout flArrivalTime;
    @BindView(R.id.llTable)
    LinearLayout llTable;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSure)
    Button btnSure;

    /**
     * 时间
     */
    private long time = 0L;
    private int table = 0;

    private OnDialogClick mOnDialogClick;
    private TimePickerView mTimePickerView;
    private OptionsPickerView mOptionsPickerView;

    public SetArrivalDialog(@NonNull Context context, OnDialogClick onDialogClick) {
        super(context);
        mOnDialogClick = onDialogClick;
    }

    @Override
    public void initView() {
        initTimePicker();
        initCustomOptionPicker();
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        //时间选择器
        mTimePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = date.getTime();
                tvArrivalTime.setText(TimeUtils.longToString(time, TimeUtils.TIME_FORMAT));
            }
        })
                .setLayoutRes(R.layout.layout_time_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isCenterLabel(true)
                .setDividerColor(ResourceUtils.getColor(mContext, R.color.theme_color))
                .setDate(selectedDate)
                .setDecorView(flArrivalTime)
                .setOutSideCancelable(false)
                .build();

        mTimePickerView.setKeyBackCancelable(false);
        mTimePickerView.show(false);
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        mOptionsPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                table = options1 + 1;
                tvTable.setText(table + "桌");
            }
        })
                .setLayoutRes(R.layout.layout_table_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setLabels("桌", "", "")
                .isCenterLabel(true)
                .setDividerColor(ResourceUtils.getColor(mContext, R.color.theme_color))
                .setOutSideCancelable(false)
                .setDecorView(llTable)
                .isDialog(false)
                .build();

        mOptionsPickerView.setKeyBackCancelable(false);
        mOptionsPickerView.show(false);

        List<String> stringList = Arrays.asList(mContext.getResources().getStringArray(R.array.table_array));
        mOptionsPickerView.setPicker(stringList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_set_arrival;
    }

    @OnClick({R.id.ivBack, R.id.rlArrivalTime, R.id.rlTable, R.id.btnCancel, R.id.btnSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                llEdit.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
                flArrivalTime.setVisibility(View.GONE);
                llTable.setVisibility(View.GONE);
                break;
            case R.id.rlArrivalTime:
                ivBack.setVisibility(View.VISIBLE);
                llEdit.setVisibility(View.GONE);
                flArrivalTime.setVisibility(View.VISIBLE);
                break;
            case R.id.rlTable:
                ivBack.setVisibility(View.VISIBLE);
                llEdit.setVisibility(View.GONE);
                llTable.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnSure:
                if (llEdit.getVisibility() == View.GONE) {
                    llEdit.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.GONE);
                    flArrivalTime.setVisibility(View.GONE);
                    llTable.setVisibility(View.GONE);
                    mTimePickerView.returnData();
                    mOptionsPickerView.returnData();
                    return;
                }
                if (time == 0L) {
                    ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "时间不能为空");
                    return;
                }
                if (table == 0) {
                    ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "桌号不能为空");
                    return;
                }
                mOnDialogClick.onSureClick(time, table);
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnDialogClick {
        /**
         * 点击确认按钮
         *
         * @param time
         * @param table
         */
        void onSureClick(long time, int table);
    }
}
