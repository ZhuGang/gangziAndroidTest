package com.test.gangzi.gangziandroidtest.data;

import com.test.gangzi.gangziandroidtest.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangzi on 2015/6/13.
 */
@Module
public class AppServiceModule {

    @Provides
    User provideUser() {
        User user = new User();
        user.setId("1");
        user.setName("hello world");
        return user;
    }
}
