package com.test.gangzi.gangziandroidtest;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangzi on 2015/6/9.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application=application;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return application;
    }
}
