package com.test.gangzi.gangziandroidtest;

import android.app.Application;
import android.content.Context;

import com.test.gangzi.gangziandroidtest.data.AppServiceModule;
import com.test.gangzi.gangziandroidtest.data.api.ApiServiceModule;

/**
 * Created by gangzi on 2015/6/9.
 */
public class AppApplication  extends Application {

    private AppComponent appComponent;


    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .appServiceModule(new AppServiceModule())
                .build();
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }
}
