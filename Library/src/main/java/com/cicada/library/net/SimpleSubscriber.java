package com.cicada.library.net;

import com.cicada.library.utils.LogUtil;

import rx.Subscriber;

/**
 * ClassName: SimpleSubscriber
 * Description: say something
 * Creator: chenwei
 * Date: 16/9/18 11:32
 * Version: 1.0
 */
public class SimpleSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted ()
        {
            LogUtil.d ("NanNongYiNong----->>", "完成请求");
        }

    @Override
    public void onError (Throwable e) {
        e.printStackTrace();
        LogUtil.d ("NanNongYiNong----->>", "完成错误");
    }

    @Override
    public void onNext (T t)
        {
            LogUtil.d ("NanNongYiNong----->>", "请求完成");
        }

}
