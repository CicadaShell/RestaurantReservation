package com.njupt.cicada.restaurantreservation.pojo;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */
public class ReservationCount {

    private int bigCount = 0;
    private int smallCount = 0;

    public int getBigCount() {
        return bigCount;
    }

    public void setBigCount(int bigCount) {
        this.bigCount = bigCount;
    }

    public int getSmallCount() {
        return smallCount;
    }

    public void setSmallCount(int smallCount) {
        this.smallCount = smallCount;
    }

    public void addBigCount() {
        this.bigCount ++;
    }

    public void addSmallCount() {
        this.smallCount ++;
    }
}
