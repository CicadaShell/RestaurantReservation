package com.cicada.library.net;

import com.cicada.library.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ClassName: ApiService
 * Description: 网络请求类，用于响应服务器
 * Creator: 张飞
 * Date: 16/8/27 17:14
 * Version: 1.0
 */

public class ApiService {
    //读取新连接超时时间
    private static final int TIMEOUT_READ = 20;
    //请求超时时间超时时间
    private static final int TIMEOUT_CONNECTION = 10;
    //日志请求和响应信息拦截器
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    //服务器访问ip地址
    private static String SERVICE_URL = null;

    private static Retrofit retrofit = null;

    private static String currUrl = null;

    //获取服务器访问ip地址
    public static String getUrl() {
        return SERVICE_URL;
    }

    private static PublicParamsCreator publicParamsCreator = null;

    /**
     * 初始化网络配置
     *
     * @param serviceUrl 服务器IP地址
     * @param newBuilder 配置构建器
     */
    public static void init(String serviceUrl, OkHttpClient.Builder newBuilder) {
        SERVICE_URL = serviceUrl;
        okHttpBuilder = newBuilder;
        currUrl = serviceUrl;
    }


    /**
     * 初始化网络配置
     *
     * @param serviceUrl 服务器IP地址
     */
    public static void init(String serviceUrl) {
        init(serviceUrl, okHttpBuilder);
    }

    public static PublicParamsCreator getPublicParamsCreator() {
        return publicParamsCreator;
    }

    public static void setPublicParamsCreator(PublicParamsCreator publicParamsCreator) {
        ApiService.publicParamsCreator = publicParamsCreator;
    }

    /**
     * 默认构建器
     */
    private static OkHttpClient.Builder okHttpBuilder = newBuilder(new OkHttpClient().newBuilder());

    /**
     * 默认构建器
     */
    public static OkHttpClient.Builder newBuilder(OkHttpClient.Builder builder) {
        return builder
                //time out
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true);
    }

    /**
     * 默认构建器
     */
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    /**
     * 创建请求响应
     *
     * @param clazz 自定义接口类
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, SERVICE_URL);
    }

    /**
     * 创建请求响应
     *
     * @param clazz 自定义接口类
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class<T> clazz, String serviceUrl) {
        return createApi(clazz, serviceUrl, null);
    }

    /**
     * 创建请求响应
     *
     * @param clazz      自定义接口类
     * @param newBuilder 构建器
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class<T> clazz, OkHttpClient.Builder newBuilder) {
        return createApi(clazz, SERVICE_URL, newBuilder);
    }


    /**
     * 创建请求响应
     *
     * @param serviceUrl 服务器IP地址
     * @param clazz      自定义接口类
     * @param newBuilder 构建器
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class<T> clazz, String serviceUrl, OkHttpClient.Builder newBuilder) {
        if (SERVICE_URL == null || "".equals(SERVICE_URL)) {
            throw new NullPointerException("serviceUrl未初始化");
        }
        if (retrofit == null || !currUrl.equals(serviceUrl)) {
            currUrl = serviceUrl;
            if (newBuilder != null) {
                synchronized (ApiService.class) {
                    OkHttpClient.Builder builde = newBuilder;
                    if (LogUtil.isDebug()) {
                        builde.addInterceptor(interceptor);
                    }
                    if (publicParamsCreator != null) {
                        builde.addInterceptor(new CommonInterceptor(publicParamsCreator));

                    }
                    Retrofit retrofit = retrofitBuilder.baseUrl(serviceUrl)
                            .client(builde.build())
                            .build();
                    return retrofit.create(clazz);
                }
            } else {
                if (okHttpBuilder.interceptors().size() > 0) {
                    okHttpBuilder.interceptors().clear();
                }
                if (LogUtil.isDebug()) {
                    okHttpBuilder.addInterceptor(interceptor);
                }
                if (publicParamsCreator != null) {
                    okHttpBuilder.addInterceptor(new CommonInterceptor(publicParamsCreator));
                }
                retrofit = retrofitBuilder.baseUrl(serviceUrl)
                        .client(okHttpBuilder.build())
                        .build();
            }

        }
        return retrofit.create(clazz);
    }


}

