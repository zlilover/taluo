package com.fairytale.fortunetarot.http;

import com.fairytale.fortunetarot.comm.Config;
import com.fairytale.fortunetarot.http.response.ResponseIntercepter;
import com.fairytale.fortunetarot.util.Logger;
import com.fairytale.fortunetarot.util.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lizhen on 2018/1/9.
 */

public class HttpRequestBuilder {
    private Retrofit mRetrofit;
    private OkHttpClient.Builder mBuilder;
    private static final int DEFAULT_CONN_TIMEOUT = 5000;
    private static final int DEFAULT_READ_TIMEOUT = 10000;

    private static class LazyLoader {
        private static final HttpRequestBuilder INSTANCE = new HttpRequestBuilder();
    }

    private HttpRequestBuilder(){
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        mBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        mBuilder.addInterceptor(new ResponseIntercepter());

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d("request message","---->"+ Util.decode(message));
            }
        });
        loggingInterceptor.setLevel(level);
        mBuilder.addInterceptor(loggingInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(mBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
    }

    public static HttpRequestBuilder getInstance() {
        return LazyLoader.INSTANCE;
    }

    public <T> T createService(Class  cls){
        return (T)mRetrofit.create(cls);
    }

}
