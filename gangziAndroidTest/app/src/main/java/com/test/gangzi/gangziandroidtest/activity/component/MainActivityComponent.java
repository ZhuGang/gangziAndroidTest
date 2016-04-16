package com.test.gangzi.gangziandroidtest.activity.component;

import com.test.gangzi.gangziandroidtest.AppComponent;
import com.test.gangzi.gangziandroidtest.activity.ActivityScope;
import com.test.gangzi.gangziandroidtest.activity.MainActivity;
import com.test.gangzi.gangziandroidtest.activity.module.MainActivityModule;
import com.test.gangzi.gangziandroidtest.activity.presenter.MainActivityPresenter;

import dagger.Component;

/**
 * Created by gangzi on 2015/6/10.
 */
@ActivityScope
@Component(modules = MainActivityModule.class,dependencies = AppComponent.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);

    MainActivityPresenter presenter();


}
