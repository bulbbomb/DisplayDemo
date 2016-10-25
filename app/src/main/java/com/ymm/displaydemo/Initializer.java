package com.ymm.displaydemo;

import com.ymm.lib.loader.ImageLoader;
import com.ymm.lib.loader.ImageSetting;
import com.ymm.lib.loader.impl.glide.ImageFrameworkGlide;
import com.ymm.lib.network.core.CommonConfig;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SHI on 16/10/25
 */

public class Initializer {

    public static void init() {
        initNetwork();

        initImageConfig();
    }

    private static void initNetwork() {
        //初始化CommonConfig
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//log request/response line,headers,body
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);// 无log
        }
        okHttpBuilder
                .addInterceptor(loggingInterceptor)//log before encrypt
//                .addInterceptor(new CommonInterceptor())//common interceptor use to interceptor request with configuration headers
//                .addInterceptor(new EncryptInterceptor())//encrypt
                .addInterceptor(loggingInterceptor);//log after encrypt
        List<Converter.Factory> converterFactories = new ArrayList<>();
        converterFactories.add(GsonConverterFactory.create());
        CommonConfig.init(ApiConstants.BASE_URL, converterFactories, okHttpBuilder);
    }

    private static void initImageConfig() {
        ImageLoader.getInstance().init(new ImageSetting.ImageSettingBuilder(ImageFrameworkGlide.getInstance())
//                .diskCachePath("/sdcard/load/cache/")
                .build());

    }
}
