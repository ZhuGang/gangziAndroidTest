package com.test.gangzi.gangziandroidtest.activity.presenter;

import com.test.gangzi.gangziandroidtest.model.User;
import com.test.gangzi.gangziandroidtest.activity.MainActivity;

/**
 * Created by gangzi on 2015/6/10.
 */
public class MainActivityPresenter {

    private MainActivity mainActivity;
    private User user;

    public MainActivityPresenter(MainActivity mainActivity, User user) {
        this.mainActivity = mainActivity;
        this.user = user;
    }


    public void showUserName(){
        mainActivity.setTextView(user.getName());
    }


}
