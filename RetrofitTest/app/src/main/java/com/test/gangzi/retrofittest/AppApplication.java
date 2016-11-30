package com.test.gangzi.retrofittest;

import android.app.Application;

import com.xiaoka.android.common.http.XKRetrofitClient;

/**
 * Created by gangzi on 16/5/12.
 */
public class AppApplication extends Application {
    private static XKRetrofitClient restClient;
    private static final String API_URL = "https://api.github.com";

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new XKRetrofitClient.Build()
                .setHost(API_URL)
                .setLogLevel(1)
                .create();
    }

    public static XKRetrofitClient getRestClient() {
        return restClient;
    }
}
