package com.test.gangzi.gangziandroidtest;

import android.app.Application;

import com.test.gangzi.gangziandroidtest.data.AppServiceModule;
import com.test.gangzi.gangziandroidtest.data.api.ApiService;
import com.test.gangzi.gangziandroidtest.data.api.ApiServiceModule;
import com.test.gangzi.gangziandroidtest.model.User;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gangzi on 2015/6/9.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class, AppServiceModule.class})
public interface AppComponent {


    Application getApplication();

    ApiService getService();

    User getUser();
}
