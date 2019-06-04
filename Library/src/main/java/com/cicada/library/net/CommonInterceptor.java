package com.cicada.library.net;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author guocongcong
 * @Date 2018/7/16
 * @Describe 公共参数拦截器
 */

public class CommonInterceptor implements Interceptor {

    private PublicParamsCreator creator ;

    public CommonInterceptor(PublicParamsCreator creator) {
        this.creator = creator;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        /**
         * 添加请求头
         */
        Request request = chain.request().newBuilder()
                .headers(Headers.of(creator.createPublicParams()))
                .build();

        return chain.proceed(request);
    }

}
