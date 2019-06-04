package com.njupt.cicada.restaurantreservation.enums;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public enum ReservationTimeEnum {

    /**
     * 中午
     */
    NOON(1),
    /**
     * 晚上
     */
    NIGHT(2);

    private int value;

    ReservationTimeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
