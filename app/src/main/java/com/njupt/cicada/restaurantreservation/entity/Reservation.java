package com.njupt.cicada.restaurantreservation.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.njupt.cicada.restaurantreservation.enums.ReservationStatusEnum;

/**
 * @Author guocongcong
 * @Date 2019/6/2
 * @Describe
 */

@Table("t_reservation")
public class Reservation {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("id")
    private int id;

    @Column("year")
    private int year;

    @Column("month")
    private int month;

    @Column("day")
    private int day;

    @Column("name")
    private String name;

    @Column("phone")
    private String phone;

    /**
     * 人数
     */
    @Column("num_of_people")
    private int numOfPeople;

    /**
     * 桌子类型（大桌、小桌）
     */
    @Column("table_type")
    private int tableType;

    @Column("remark")
    private String remark;
    /**
     * 预约时间（中午、晚上）
     */
    @Column("time_type")
    private int timeType;
    /**
     * 订单状态（预约、入座、取消）
     */
    @Default("1")
    @Column("status")
    private int status;

    @Column("arrivalTime")
    private long arrivalTime;

    @Column("table_num")
    private int tableNum;

    public Reservation(int year, int month, int day, String name, String phone, int numOfPeople, int tableType, String remark, int timeType) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.name = name;
        this.phone = phone;
        this.numOfPeople = numOfPeople;
        this.tableType = tableType;
        this.remark = remark;
        this.timeType = timeType;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phoneNum) {
        this.phone = phoneNum;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}
