package com.cicada.library.utils;

import android.content.Context;

import com.cicada.library.net.ApiException;
import com.sdsmdg.tastytoast.TastyToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * @Author guocongcong
 * @Date 2018/7/30
 * @Describe 消息提示帮助类
 */
public class ToastUtils {

    public static final int SUCCESS = TastyToast.SUCCESS;
    public static final int WARNING = TastyToast.WARNING;
    public static final int ERROR = TastyToast.ERROR;
    public static final int INFO = TastyToast.INFO;
    public static final int DEFAULT = TastyToast.DEFAULT;
    public static final int CONFUSING = TastyToast.CONFUSING;

    private int style = SUCCESS;

    public ToastUtils(int style) {
        this.style = style;
    }

    public static ToastUtils init(int style) {
        return new ToastUtils(style);
    }

    /**
     * LENGTH_SHORT
     */
    public void shortShow(Context context, String msg) {
        TastyToast.makeText(context, msg, TastyToast.LENGTH_SHORT, style);
    }

    public void shortShow(Context context, int msg) {
        TastyToast.makeText(context, ResourceUtils.getString(context, msg), TastyToast.LENGTH_SHORT, style);
    }

    /**
     * LENGTH_LONG
     */
    public void longShow(Context context, String msg) {
        TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, style);
    }

    public void longShow(Context context, int msg) {
        TastyToast.makeText(context, ResourceUtils.getString(context, msg), TastyToast.LENGTH_LONG, style);
    }

    /**
     * 处理网络请求异常
     *
     * @param throwable
     * @param context
     */
    public static void handleThrowable(Throwable throwable, final Context context) {
        if (throwable instanceof ApiException) {

        } else if (throwable instanceof ConnectException) {
            //TODO:网络连接异常
            ToastUtils.init(ERROR).shortShow(context, "网络连接异常");
        } else if (throwable instanceof HttpException) {
            //TODO:服务器异常
            ToastUtils.init(ERROR).shortShow(context, "服务器异常");
        } else if (throwable instanceof SocketTimeoutException) {
            //TODO:连接超时
            ToastUtils.init(ERROR).shortShow(context, "连接超时");
        }
    }

}
