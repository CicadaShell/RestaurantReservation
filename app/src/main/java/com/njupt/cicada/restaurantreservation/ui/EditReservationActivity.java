package com.njupt.cicada.restaurantreservation.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cicada.library.utils.DBUtils;
import com.cicada.library.utils.ResourceUtils;
import com.cicada.library.utils.RxManager;
import com.cicada.library.utils.TimeUtils;
import com.cicada.library.utils.ToastUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.base.ToolbarActivity;
import com.njupt.cicada.restaurantreservation.constants.AppConstants;
import com.njupt.cicada.restaurantreservation.constants.RxJavaConstants;
import com.njupt.cicada.restaurantreservation.entity.Reservation;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;
import com.njupt.cicada.restaurantreservation.enums.ReservationTimeEnum;
import com.njupt.cicada.restaurantreservation.enums.TableTypeEnum;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public class EditReservationActivity extends ToolbarActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.rlDate)
    RelativeLayout rlDate;
    @BindView(R.id.tvNight)
    TextView tvNight;
    @BindView(R.id.ivNight)
    ImageView ivNight;
    @BindView(R.id.flNight)
    FrameLayout flNight;
    @BindView(R.id.tvNoon)
    TextView tvNoon;
    @BindView(R.id.ivNoon)
    ImageView ivNoon;
    @BindView(R.id.flNoon)
    FrameLayout flNoon;
    @BindView(R.id.rlTimeType)
    RelativeLayout rlTimeType;
    @BindView(R.id.tvNumOfPeople)
    TextView tvNumOfPeople;
    @BindView(R.id.rlNumOfPeople)
    RelativeLayout rlNumOfPeople;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.tvDelete)
    TextView tvDelete;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    private DatePickerDialog mDatePickerDialog;
    private OptionsPickerView mOptionsPickerView;

    private int id;
    private String name;
    private String phone;
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int timeType = 0;
    private int numOfPeople = 0;
    private int tableType = 0;
    private String remark;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditReservationActivity.class);
        return intent;
    }

    public static Intent createIntent(Context context, int id) {
        Intent intent = new Intent(context, EditReservationActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (id == 0) {
            tvTitle.setText("新建预约");
        } else {
            tvTitle.setText("编辑预约");
        }
    }

    @Override
    protected void initIntentValue() {
        super.initIntentValue();
        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    public void initView() {
        super.initView();

        if (id == 0) {
            tvDelete.setVisibility(View.GONE);
            tvSubmit.setText("新建");
        } else {
            tvDelete.setVisibility(View.VISIBLE);
            tvSubmit.setText("保存");
        }

        initData();
        initSelectDate();
        initCustomOptionPicker();
    }

    private void initData() {
        if (id == 0) {
            return;
        }

        List<Reservation> reservationList = DBUtils.getQueryByWhere(Reservation.class, "id", String.valueOf(id));
        if (reservationList != null && reservationList.size() > 0) {
            Reservation reservation = reservationList.get(0);
            etName.setText(reservation.getName());
            etPhone.setText(reservation.getPhone());
            year = reservation.getYear();
            month = reservation.getMonth();
            day = reservation.getDay();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(reservation.getYear());
            stringBuilder.append("-");
            stringBuilder.append(reservation.getMonth());
            stringBuilder.append("-");
            stringBuilder.append(reservation.getDay());
            tvDate.setText(stringBuilder.toString());
            timeType = reservation.getTimeType();
            changeTimeType(timeType);
            numOfPeople = reservation.getNumOfPeople();
            tvNumOfPeople.setText(numOfPeople + "人");
            etRemark.setText(reservation.getRemark());
        }
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        mOptionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                numOfPeople = options1 + 1;
                tableType = numOfPeople > 4 ? TableTypeEnum.BIG.getValue() : TableTypeEnum.SMALL.getValue();
                tvNumOfPeople.setText(numOfPeople + "人");
            }
        })
                .setLayoutRes(R.layout.dialog_select_num, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        v.findViewById(R.id.btnSure).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsPickerView.returnData();
                                mOptionsPickerView.dismiss();
                            }
                        });

                        v.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsPickerView.dismiss();
                            }
                        });
                    }
                })
                .setLabels("人", "", "")
                .isCenterLabel(true)
                .setDividerColor(ResourceUtils.getColor(mContext, R.color.theme_color))
                .setOutSideCancelable(false)
                .isDialog(true)
                .build();

        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.num_array));
        mOptionsPickerView.setPicker(stringList);
    }

    private void initSelectDate() {
        final Calendar calendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1 + 1;
                day = i2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year);
                stringBuilder.append("-");
                stringBuilder.append(month);
                stringBuilder.append("-");
                stringBuilder.append(day);
                tvDate.setText(stringBuilder.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void changeTimeType(int type) {
        switch (type) {
            case 1:
                timeType = ReservationTimeEnum.NOON.getValue();
                flNoon.setSelected(true);
                tvNoon.setTextColor(ResourceUtils.getColor(mContext, R.color.theme_color));
                ivNoon.setVisibility(View.VISIBLE);
                flNight.setSelected(false);
                tvNight.setTextColor(ResourceUtils.getColor(mContext, R.color.gray_text_color));
                ivNight.setVisibility(View.GONE);
                break;
            case 2:
                timeType = ReservationTimeEnum.NIGHT.getValue();
                flNight.setSelected(true);
                tvNight.setTextColor(ResourceUtils.getColor(mContext, R.color.theme_color));
                ivNight.setVisibility(View.VISIBLE);
                flNoon.setSelected(false);
                tvNoon.setTextColor(ResourceUtils.getColor(mContext, R.color.gray_text_color));
                ivNoon.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private boolean checkCorrectness() {
        name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "姓名不能为空");
            return false;
        }
        phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "手机号不能为空");
            return false;
        }
        if (year == 0 || month == 0 || day == 0) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "日期不能为空");
            return false;
        }
        if (timeType == 0) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "时间段不能为空");
            return false;
        }
        if (numOfPeople < 1 || numOfPeople > 8) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "人数不能为空");
            return false;
        }
        return true;
    }

    private boolean checkTable() {

        String[] field = new String[]{"year", "month", "day", "time_type", "table_type", "status"};
        String[] value = new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(timeType), String.valueOf(tableType), ""};
        value[5] = String.valueOf(ReservationStatusEnum.UNDERWAY.getValue());
        int count = (int) DBUtils.getQuerySizeByWhere(Reservation.class, field, value);
        value[5] = String.valueOf(ReservationStatusEnum.SEAT.getValue());
        count += (int) DBUtils.getQuerySizeByWhere(Reservation.class, field, value);
        if (count >= (tableType == TableTypeEnum.SMALL.getValue() ? AppConstants.SMALL_TABLE_COUNT : AppConstants.BIG_TABLE_COUNT)) {
            ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "当前座位已满");
            return false;
        }

        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_reservation;
    }

    @OnClick({R.id.flNight, R.id.flNoon, R.id.rlDate, R.id.rlNumOfPeople, R.id.tvDelete, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flNoon:
                changeTimeType(1);
                break;
            case R.id.flNight:
                changeTimeType(2);
                break;
            case R.id.rlDate:
                mDatePickerDialog.show();
                break;
            case R.id.rlNumOfPeople:
                mOptionsPickerView.show();
                break;
            case R.id.tvDelete:
                if (id != 0) {
                    DBUtils.deleteById(Reservation.class, "id", String.valueOf(id));
                    RxManager rxManager = new RxManager();
                    rxManager.post(RxJavaConstants.RX_RESERVATION_CHANGE, "");
                    finish();
                }
                break;
            case R.id.tvSubmit:
                if (checkCorrectness() && checkTable()) {
                    remark = etRemark.getText().toString();
                    Reservation reservation = new Reservation(year, month, day, name, phone, numOfPeople, tableType, remark, timeType);
                    if (id == 0) {
                        DBUtils.insert(reservation);
                    } else {
                        reservation.setId(id);
                        DBUtils.update(reservation);
                    }
                    RxManager rxManager = new RxManager();
                    rxManager.post(RxJavaConstants.RX_RESERVATION_CHANGE, "");
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
