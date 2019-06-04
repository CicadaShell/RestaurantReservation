package com.njupt.cicada.restaurantreservation.enums;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public enum ReservationStatusEnum {

    /**
     * 预约中
     */
    UNDERWAY(0),
    /**
     * 入座
     */
    SEAT(1),
    /**
     * 取消
     */
    CANCEL(2);

    private int value;

    ReservationStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
