package com.cicada.library.net;

import com.cicada.library.pojo.JsonEntity;
import com.cicada.library.utils.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hpw on 16/11/2.
 */

public class APiUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }

    /**
     * 统一返回数据请求结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Transformer<JsonEntity<T>, T> handleResult() {   //compose判断结果
        return new Transformer<JsonEntity<T>, T>() {
            @Override
            public Observable<T> call(Observable<JsonEntity<T>> jsonEntityObservable) {
                return jsonEntityObservable.map(new HttpResultFunc<T>());
            }
        };
    }

    /**
     * 统一返回下载结果处理
     *
     * @return
     * @Param filePath 文件路径
     */
    public static Transformer<ResponseBody, ResponseBody> handleDownLoadResult(final File file) {   //compose判断结果

        return new Transformer<ResponseBody, ResponseBody>() {

            @Override
            public Observable call(Observable<ResponseBody> responseBodyObservable) {
                return responseBodyObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new Func1<ResponseBody, InputStream>() {
                            @Override
                            public InputStream call(ResponseBody responseBody) {
                                return responseBody.byteStream();
                            }
                        }).observeOn(Schedulers.computation())
                        .doOnNext(new Action1<InputStream>() {
                            @Override
                            public void call(InputStream inputStream) {

                                FileUtil.write2Disk(inputStream, file);
                            }
                        }).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }
}
