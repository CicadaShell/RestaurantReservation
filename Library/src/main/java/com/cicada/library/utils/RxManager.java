package com.cicada.library.utils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by hpw on 16/10/27.
 */

public class RxManager {

    public RxBus mRxBus = RxBus.$();
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者


    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        mCompositeSubscription.clear();// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

    public void post(String eventName, Object content) {
        mRxBus.post(eventName, content);
    }
}