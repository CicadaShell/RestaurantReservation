package com.cicada.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author guocongcong
 * @Date 2018/7/30
 * @Describe 时间工具类
 */
public class TimeUtils {

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 天时间格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 天时间格式
     */
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * 获取当前日期的Calendar(无时分秒)
     * @return
     */
    public static final Calendar getTodayCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);//控制时
        calendar.set(Calendar.MINUTE, 0);//控制分
        calendar.set(Calendar.SECOND, 0);//控制秒
        calendar.set(Calendar.MILLISECOND, 0);//控制毫秒
        return calendar;
    }

    /**
     * long -> string (默认格式)
     *
     * @param time
     * @return
     */
    public static String longToString(long time) {
        return longToString(time, DEFAULT_FORMAT);
    }

    /**
     * long -> string
     *
     * @param time
     * @param format
     * @return
     */
    public static String longToString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    /**
     * string -> date (默认格式)
     *
     * @param time
     * @return
     */
    public static Date stringToDate(String time) {
        return stringToDate(time, DEFAULT_FORMAT);
    }

    /**
     * string -> date
     *
     * @param time
     * @param format
     * @return
     */
    public static Date stringToDate(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * string -> long
     *
     * @param time
     * @param format
     * @return
     */
    public static long stringToLong(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * string -> calendar
     *
     * @param time
     * @param format
     * @return
     */
    public static Calendar stringToCalendar(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(time));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date -> string (默认格式)
     *
     * @param time
     * @return
     */
    public static String dateToString(Date time) {
        return dateToString(time, DEFAULT_FORMAT);
    }

    /**
     * date -> string
     *
     * @param time
     * @param format
     * @return
     */
    public static String dateToString(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }


}
