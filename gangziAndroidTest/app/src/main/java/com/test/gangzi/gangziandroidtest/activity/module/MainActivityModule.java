package com.test.gangzi.gangziandroidtest.activity.module;

import com.test.gangzi.gangziandroidtest.model.User;
import com.test.gangzi.gangziandroidtest.activity.ActivityScope;
import com.test.gangzi.gangziandroidtest.activity.MainActivity;
import com.test.gangzi.gangziandroidtest.activity.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangzi on 2015/6/10.
 */
@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Provides
    @ActivityScope
    MainActivity provideMainActivity() {
        return mainActivity;
    }


    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(MainActivity mainActivity, User user) {
        return new MainActivityPresenter(mainActivity, user);
    }


}
