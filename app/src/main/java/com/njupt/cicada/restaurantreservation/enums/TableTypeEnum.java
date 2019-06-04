package com.njupt.cicada.restaurantreservation.enums;

/**
 * @Author guocongcong
 * @Date 2019/6/4
 * @Describe
 */
public enum TableTypeEnum {

    /**
     * 小桌
     */
    SMALL(1),
    /**
     * 大桌
     */
    BIG(2);

    private int value;

    TableTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
